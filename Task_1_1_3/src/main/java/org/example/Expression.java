package org.example;


import java.util.Map;
// Абстрактный класс Expression
public abstract class Expression {
    // Метод для вычисления значения выражения
    public abstract double evaluate(Map<String, Double> variables);

    public abstract Expression derivative(String variable);
}
