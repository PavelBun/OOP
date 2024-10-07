package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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

        Map<String, Double> result = Main.parseVariables(assignments);

        assertEquals(expected, result);
    }

    // Тест для выражения Add(Number(3), Mul(Number(2), Variable("x")))
    @Test
    public void testExpressionEvaluation() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));

        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 10.0);

        double result = e.evaluate(variables);
        assertEquals(23.0, result, 0.001);
    }

    // Тест на случай отсутствия переменной
    // Тест на случай отсутствия переменной
    @Test
    public void testExpressionMissingVariable() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("y")));

        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 10.0);  // Переменной "y" нет в карте

        try {
            e.evaluate(variables);
            fail("Expected an exception for missing variable");
        } catch (IllegalArgumentException ex) {
            assertEquals("Variable y not defined", ex.getMessage());  // Обновлено ожидание
        }
    }


    // Тест для выражения без переменных (например, просто числа)
    @Test
    public void testExpressionWithNoVariables() {
        Expression e = new Add(new Number(5), new Number(10));

        Map<String, Double> variables = new HashMap<>();  // Пустая карта переменных

        double result = e.evaluate(variables);
        assertEquals(15.0, result, 0.001);
    }
}
