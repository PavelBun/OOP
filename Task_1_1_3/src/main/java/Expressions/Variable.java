package Expressions;

import org.example.Expression;
import org.example.UndefinedVariableException;

import java.util.Map;



public class Variable extends Expression {
    private final String var;

    public Variable(String name) {
        this.var = name;
    }

    @Override
    public double evaluate(Map<String, Double> variables){
        if (variables.containsKey(var)) {
            return variables.get(var);
        } else {
            throw new UndefinedVariableException("Variable " + var + " not defined");
        }
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public Expression derivative(String variable) {
        if (var.equals(variable)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }
}