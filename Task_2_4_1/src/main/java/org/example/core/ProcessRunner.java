package org.example.core;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ProcessRunner {
    public static ProcessResult runCommand(Path directory, String command, Duration timeout)
            throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command.split(" "))
                .directory(directory.toFile())
                .redirectErrorStream(true);

        Process process = pb.start();
        boolean completed = process.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);

        if (!completed) {
            process.destroy();
            throw new RuntimeException("Process timed out");
        }

        String output = new String(process.getInputStream().readAllBytes());
        String error = new String(process.getErrorStream().readAllBytes());
        return new ProcessResult(process.exitValue(), output, error);
    }
}