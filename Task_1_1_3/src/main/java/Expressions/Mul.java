package Expressions;
import org.example.Expression;

import java.util.Map;
public class Mul extends Expression {
    private final Expression first;
    private final Expression second;
    public Mul(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        return first.evaluate(variables) * second.evaluate(variables);
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "*" + second.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(
                new Mul(first.derivative(variable), second),
                new Mul(first, second.derivative(variable))
        );
    }
}
