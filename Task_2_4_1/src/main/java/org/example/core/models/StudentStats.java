// StudentStats.java
package org.example.core.models;

import java.util.Map;

public class StudentStats {
    private final Map<String, Integer> taskScores;
    private final int totalScore;
    private final double activity;
    private final String grade;

    public StudentStats(Map<String, Integer> taskScores, int totalScore, double activity, String grade) {
        this.taskScores = taskScores;
        this.totalScore = totalScore;
        this.activity = activity;
        this.grade = grade;
    }

    // Геттеры
    public Map<String, Integer> getTaskScores() { return taskScores; }
    public int getTotalScore() { return totalScore; }
    public double getActivity() { return activity; }
    public String getGrade() { return grade; }
}