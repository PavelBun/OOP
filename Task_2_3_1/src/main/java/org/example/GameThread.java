package org.example;

import javafx.application.Platform;

public class GameThread extends Thread {
    private volatile boolean running = true;
    private final GameController gameController;
    private final long updateInterval;

    public GameThread(GameController gameController, long updateInterval) {
        this.gameController = gameController;
        this.updateInterval = updateInterval;
    }

    @Override
    public void run() {
        System.out.println("[DEBUG] GameThread started");
        long lastUpdateTime = System.nanoTime();
        while (running) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastUpdateTime;

            if (elapsedTime >= updateInterval) {
                lastUpdateTime = currentTime;

                Platform.runLater(() -> {
                    boolean continueRunning = gameController.updateGameState();
                    gameController.render();
                    if (!continueRunning) {
                        System.out.println("[DEBUG] Game stopping...");
                        stopGame();
                    }
                });
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("[DEBUG] Thread interrupted");
                running = false;
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("[DEBUG] GameThread stopped");
    }

    public void stopGame() {
        running = false;
        interrupt();
    }
}