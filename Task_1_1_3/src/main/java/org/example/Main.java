package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));

        // Пример строки означивания переменных
        String assignments = "x=10; y=13";
        Map<String, Double> variables = parseVariables(assignments);

        System.out.println("Expression: " + e);
        System.out.println("Value: " + e.evaluate(variables));
    }

    // Метод для парсинга строки с означиванием переменных
    public static Map<String, Double> parseVariables(String input) {
        Map<String, Double> variables = new HashMap<>();
        String[] assignments = input.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            String name = parts[0].trim();
            double value = Double.parseDouble(parts[1].trim());
            variables.put(name, value);
        }
        return variables;
    }
}
