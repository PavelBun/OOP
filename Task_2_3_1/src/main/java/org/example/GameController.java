package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {
    @FXML private Canvas gameCanvas;
    private GameLogic gameLogic;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private Image foodImage;
    private Image backgroundImage;

    @FXML
    public void initialize() {
        backgroundImage = new Image(getClass().getResourceAsStream("/images/grass.png"));
        foodImage = new Image(getClass().getResourceAsStream("/images/apple.png"));

        gc = gameCanvas.getGraphicsContext2D();
        gameLogic = new GameLogic(20, 20, 10);

        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();

        gameCanvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::handleKeyPress);
            }
        });

        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 300_000_000) { // скорость змейки
                    update();
                    render();
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();

        System.out.println("Game loop started!");
    }

    private void render() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        gc.drawImage(backgroundImage, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Проверяем, что змейка обновляется
        System.out.println("Rendering snake at:");

        // В методе render()
        gc.setFill(Color.LIME);
        for (Point2D segment : gameLogic.getSnake().getBody()) {
            gc.fillRoundRect(
                    segment.getX() * 20 + 1,
                    segment.getY() * 20 + 1,
                    18, 18, 5, 5
            );
        }

        gameLogic.getFoodManager().getFood().forEach(food ->
                gc.drawImage(foodImage, food.getX() * 20, food.getY() * 20, 20, 20)
        );

        System.out.println("Rendered frame!");
    }
    private void showGameOver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game_over.fxml"));
            Parent root = loader.load();

            GameOverController controller = loader.getController();
            controller.initialize(
                    (Stage) gameCanvas.getScene().getWindow(),
                    gameLogic.getScore()
            );

            Scene scene = new Scene(root);
            Stage stage = (Stage) gameCanvas.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        if (gameLogic.isGameOver()) {
            showGameOver();
            gameLoop.stop();
        }
        else if (!gameLogic.isGameOver() && !gameLogic.isGameWon()) {
            gameLogic.update();
            System.out.println("Updated snake position: " + gameLogic.getSnake().getBody());
        } else {
            gameLoop.stop();
            showGameOverMessage();
        }
    }

    private void showGameOverMessage() {
        gc.setFill(Color.BLACK);
        gc.fillText("Game Over! Score: " + gameLogic.getScore(), 150, 200);
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        Direction newDirection = switch (event.getCode()) {
            case UP -> Direction.UP;
            case DOWN -> Direction.DOWN;
            case LEFT -> Direction.LEFT;
            case RIGHT -> Direction.RIGHT;
            default -> null;
        };

        if (newDirection != null) {
            gameLogic.getSnake().setDirection(newDirection);
        }
        event.consume(); // Предотвращаем дублирование событий
    }
}
