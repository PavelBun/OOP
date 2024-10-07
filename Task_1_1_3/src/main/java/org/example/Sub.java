package org.example;
import java.util.Map;
public class Sub extends Expression{
    private final Expression first;
    private final Expression second;

    public Sub(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        return first.evaluate(variables) - second.evaluate(variables);
    }
    @Override
    public String toString() {
        return "(" + first.toString() + "-" + second.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(first.derivative(variable), second.derivative(variable));
    }
}
