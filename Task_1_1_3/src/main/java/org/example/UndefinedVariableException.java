package org.example;
public class UndefinedVariableException extends RuntimeException {
    public UndefinedVariableException(String message) {
        super(message);
    }
}