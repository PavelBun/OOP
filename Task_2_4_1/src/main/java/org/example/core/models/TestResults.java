// TestResults.java
package org.example.core.models;

public class TestResults {
    private final int passed;
    private final int failed;
    private final int skipped;

    public TestResults(int passed, int failed, int skipped) {
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
    }

    @Override
    public String toString() {
        return String.format("%d/%d/%d", passed, failed, skipped);
    }
}