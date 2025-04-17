package org.example.core;

import org.example.core.models.Student;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class GitService {
    public static void cloneRepository(Student student, Path tempDir) throws GitException {
        try {
            // Используйте getGithubNick() вместо githubNick()
            Path repoDir = tempDir.resolve(student.getGithubNick());
            Files.createDirectories(repoDir);

            // Используйте getRepoUrl() вместо repoUrl()
            ProcessResult result = ProcessRunner.runCommand(
                    tempDir,
                    "git clone " + student.getRepoUrl() + " " + repoDir.getFileName(),
                    Duration.ofMinutes(2)
            );

            if (result.exitCode() != 0) {
                throw new GitException("Clone failed: " + result.error());
            }
        } catch (IOException | InterruptedException e) {
            throw new GitException("Git operation failed", e);
        }
    }

    public static class GitException extends Exception {
        public GitException(String message) {
            super(message);
        }

        public GitException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}