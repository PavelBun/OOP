package org.example;

import Expressions.Add;
import Expressions.Mul;
import Expressions.Number;
import Expressions.Variable;

import java.util.Map;

import static org.example.Parser.parseVariables;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new Expressions.Number(2), new Mul(new Number(1), new Variable("x")));

        // Пример строки означивания переменных
        String assignments = "x=10; y=13";
        Map<String, Double> variables = parseVariables(assignments);

        try {
            System.out.println("Expression: " + e);
            System.out.println("Value: " + e.evaluate(variables));
        } catch (UndefinedVariableException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}