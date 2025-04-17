package org.example.core.models;

public class Student {
    private final String githubNick;
    private final String fullName;
    private final String repoUrl;

    public Student(String githubNick, String fullName, String repoUrl) {
        this.githubNick = githubNick;
        this.fullName = fullName;
        this.repoUrl = repoUrl;
    }

    // Геттеры
    public String getGithubNick() { return githubNick; }
    public String getFullName() { return fullName; }
    public String getRepoUrl() { return repoUrl; }
}