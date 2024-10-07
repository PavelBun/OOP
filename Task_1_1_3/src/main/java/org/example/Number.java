package org.example;

import java.util.Map;

public class Number extends Expression {
    private final double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }
}
