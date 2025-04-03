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
import java.util.List;

public class GameController {
    @FXML private Canvas gameCanvas;
    private GameLogic gameLogic;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private Image foodImage;
    private Image backgroundImage;
    private Image headImage;
    private Image tailImage;
    private static final int CELL_SIZE = 20; // Размер клетки
    private static final int BOARD_WIDTH = 17; // Ширина поля в клетках
    private static final int BOARD_HEIGHT = 15;
    @FXML
    public void initialize() {
        backgroundImage = new Image(getClass().getResourceAsStream("/images/grass.png"));
        foodImage = new Image(getClass().getResourceAsStream("/images/apple.png"));
        headImage = new Image(getClass().getResourceAsStream("/images/snake_head.png"));
        tailImage = new Image(getClass().getResourceAsStream("/images/snake_tail.png"));
        gc = gameCanvas.getGraphicsContext2D();
        gameLogic = new GameLogic(BOARD_WIDTH, BOARD_HEIGHT, BOARD_WIDTH * BOARD_HEIGHT);
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
                if (now - lastUpdate >= 150_000_000) { // скорость змейки
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

        List<Point2D> snakeBody = gameLogic.getSnake().getBody();

        if (snakeBody.isEmpty()) return;

        // Рисуем тело змейки (без головы)

        for (int i = 1; i < snakeBody.size(); i++) {
            Point2D segment = snakeBody.get(i);
            gc.drawImage(tailImage,
                    segment.getX() * CELL_SIZE,
                    segment.getY() * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE
            );
        }

        // Рисуем голову змейки отдельно
        Point2D head = snakeBody.get(0);
        gc.drawImage(headImage,
                head.getX() * CELL_SIZE,
                head.getY() * CELL_SIZE,
                CELL_SIZE,
                CELL_SIZE
        );

        // Рисуем яблоки
        gameLogic.getFoodManager().getFood().forEach(food ->
                gc.drawImage(foodImage,
                        food.getX() * CELL_SIZE,
                        food.getY() * CELL_SIZE,
                        CELL_SIZE,
                        CELL_SIZE
                )
        );
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
        event.consume();
    }
}
