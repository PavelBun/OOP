package Expressions;
import org.example.Expression;

import java.util.Map;
public class Add extends Expression {
    private final Expression first;
    private final Expression second;
    public Add(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        return first.evaluate(variables) + second.evaluate(variables);
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "+" + second.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(first.derivative(variable), second.derivative(variable));
    }
}
