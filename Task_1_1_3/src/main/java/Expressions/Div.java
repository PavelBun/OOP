package Expressions;

import org.example.Expression;

import java.util.Map;

public class Div extends Expression {
    private final Expression first;
    private final Expression second;

    public Div(Expression numerator, Expression denominator) {
        this.first = numerator;
        this.second = denominator;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        return first.evaluate(variables) / second.evaluate(variables);
    }

    @Override
    public String toString() {
        return "(" + first.toString() + " / " + second.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        // Производная частного: (f / g)' = (f' * g - f * g') / g^2
        return new Div(
                new Sub(new Mul(first.derivative(variable), second), new Mul(first, second.derivative(variable))),
                new Mul(second, second)
        );
    }
}
