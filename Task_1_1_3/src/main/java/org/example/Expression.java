package org.example;
import java.util.Map;
// Абстрактный класс Expression
public abstract class Expression {
    // Метод для вычисления значения выражения
    public abstract double evaluate(Map<String, Double> variables);

    // Метод для представления выражения в виде строки
    public abstract String toString();

    public abstract Expression derivative(String variable);
}
