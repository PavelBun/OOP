package org.example.core;

public class ProcessResult {
    private final int exitCode;
    private final String output;
    private final String error;

    public ProcessResult(int exitCode, String output, String error) {
        this.exitCode = exitCode;
        this.output = output;
        this.error = error;
    }

    // Геттеры
    public int exitCode() {
        return exitCode;
    }

    public String error() {
        return error;
    }

    public String output() {
        return output;
    }
}