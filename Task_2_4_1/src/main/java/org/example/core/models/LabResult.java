package org.example.core.models;

public class LabResult {
    private final String taskId;
    private final String studentId;
    private final boolean buildSuccess;
    private final TestResults testResults;
    private final int totalScore;

    public LabResult(String taskId, String studentId, boolean buildSuccess,
                     TestResults testResults, int totalScore) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.buildSuccess = buildSuccess;
        this.testResults = testResults;
        this.totalScore = totalScore;
    }

    // Геттеры
    public String getTaskId() { return taskId; }
    public String getStudentId() { return studentId; }
    public boolean isBuildSuccess() { return buildSuccess; }
    public TestResults getTestResults() { return testResults; }
    public int getTotalScore() { return totalScore; }
}