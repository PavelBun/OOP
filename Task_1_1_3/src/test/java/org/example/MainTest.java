package org.example;

import Expressions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class MainTest {

    // Тест для метода parseVariables
    @Test
    public void testParseVariables() {
        String assignments = "x=10; y=13";
        Map<String, Double> expected = new HashMap<>();
        expected.put("x", 10.0);
        expected.put("y", 13.0);

        Map<String, Double> result = Parser.parseVariables(assignments);

        assertEquals(expected, result);
    }

    // Тест для выражения Add(Number(3), Mul(Number(2), Variable("x")))
    @Test
    public void testExpressionEvaluation() {
        Expression e = new Add(new Expressions.Number(3), new Mul(new Expressions.Number(2), new Variable("x")));

        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 10.0);

        double result = e.evaluate(variables);
        assertEquals(23.0, result, 0.001);
    }

    // Тест на случай отсутствия переменной
    @Test
    public void testExpressionMissingVariable() {
        Expression e = new Add(new Expressions.Number(3), new Mul(new Expressions.Number(2), new Variable("y")));

        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 10.0);  // Переменной "y" нет в карте

        try {
            e.evaluate(variables);
            fail("Expected an UndefinedVariableException for missing variable");
        } catch (UndefinedVariableException ex) {
            assertEquals("Variable y not defined", ex.getMessage());
        }
    }

    // Тест для выражения без переменных (например, просто числа)
    @Test
    public void testExpressionWithNoVariables() {
        Expression e = new Add(new Expressions.Number(5), new Expressions.Number(10));

        Map<String, Double> variables = new HashMap<>();  // Пустая карта переменных

        double result = e.evaluate(variables);
        assertEquals(15.0, result, 0.001);
    }

    // Тест для выражения Sub(Number(10), Number(5))
    @Test
    public void testSubExpression() {
        Expression e = new Sub(new Expressions.Number(10), new Expressions.Number(5));

        Map<String, Double> variables = new HashMap<>();

        double result = e.evaluate(variables);
        assertEquals(5.0, result, 0.001);
    }

    // Тест для выражения Div(Number(10), Number(2))
    @Test
    public void testDivExpression() {
        Expression e = new Div(new Expressions.Number(10), new Expressions.Number(2));

        Map<String, Double> variables = new HashMap<>();

        double result = e.evaluate(variables);
        assertEquals(5.0, result, 0.001);
    }

    @Test
    public void testHighOrderDerivative() {
        // Выражение x^3
        Expression e = new Mul(new Mul(new Variable("x"), new Variable("x")), new Variable("x"));

        Expression firstDerivative = e.derivative("x");
        Expression secondDerivative = firstDerivative.derivative("x");
        Expression thirdDerivative = secondDerivative.derivative("x");

        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 2.0);

        double firstDerivativeValue = firstDerivative.evaluate(variables);
        double secondDerivativeValue = secondDerivative.evaluate(variables);
        double thirdDerivativeValue = thirdDerivative.evaluate(variables);

        assertEquals(12.0, firstDerivativeValue, 0.001);
        assertEquals(12.0, secondDerivativeValue, 0.001);
        assertEquals(6.0, thirdDerivativeValue, 0.001);
    }
}