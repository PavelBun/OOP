package org.example;

import java.util.HashMap;
import java.util.Map;

public class Parser {
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
