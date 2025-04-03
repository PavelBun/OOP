package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    @FXML private Canvas gameCanvas;
    private GameLogic gameLogic;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private Image foodImage;
    private Image backgroundImage;
    private Image headImage;
    private Image tailImage;
    private static final int CELL_SIZE = 20;
    private static final int BOARD_WIDTH = 17;
    private static final int BOARD_HEIGHT = 15;
    private int currentLevel = 1; // Уровень по умолчанию
    @FXML private Text startText;
    private boolean gameStarted = false;
    private Map<FruitType, Image> fruitImages = new HashMap<>();
    // Установка уровня из меню выбора
    public void setLevel(int level) {
        this.currentLevel = level;
        if (level == 2) {
            gameLogic = new GameLogic(BOARD_WIDTH, BOARD_HEIGHT, BOARD_WIDTH * BOARD_HEIGHT - 3, currentLevel);
        }
        if (level == 1){
            gameLogic = new GameLogic(BOARD_WIDTH, BOARD_HEIGHT, BOARD_WIDTH * BOARD_HEIGHT, currentLevel);
        }
    }

    @FXML
    public void initialize() {
        backgroundImage = new Image(getClass().getResourceAsStream("/images/grass.png"));
        for (FruitType type : FruitType.values()) {
            fruitImages.put(type, new Image(getClass().getResourceAsStream(type.getImagePath())));
        }
        headImage = new Image(getClass().getResourceAsStream("/images/snake_head.png"));
        tailImage = new Image(getClass().getResourceAsStream("/images/snake_tail.png"));
        gc = gameCanvas.getGraphicsContext2D();
        gameLogic = new GameLogic(BOARD_WIDTH, BOARD_HEIGHT, BOARD_WIDTH * BOARD_HEIGHT, currentLevel);
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
                if (now - lastUpdate >= 150_000_000) {
                    update();
                    render();
                    lastUpdate = now;
                }
            }
        };
        startText.setVisible(true); // Показываем текст перед стартом
        gameLoop = createGameLoop();


    }
    // Обновить игровой цикл для использования интервала из GameLogic
    private AnimationTimer createGameLoop() {
        return new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (!gameStarted) return;

                if (now - lastUpdate >= gameLogic.getUpdateInterval()) {
                    update();
                    render();
                    lastUpdate = now;
                }
            }
        };
    }

    private void render() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        gc.drawImage(backgroundImage, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        Snake snake = gameLogic.getSnake(); // Получаем объект змейки
        List<Point2D> snakeBody = snake.getBody();

        // Отрисовка тела
        for (int i = 1; i < snakeBody.size(); i++) {
            Point2D segment = snakeBody.get(i);
            gc.drawImage(tailImage, segment.getX() * CELL_SIZE, segment.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // Отрисовка головы с поворотом
        Point2D head = snakeBody.get(0);
        double rotation = switch (snake.getDirection()) {
            case UP -> 0;
            case DOWN -> 180;
            case LEFT -> -90;
            case RIGHT -> 90;
        };

        gc.save();
        gc.translate(head.getX() * CELL_SIZE + CELL_SIZE/2, head.getY() * CELL_SIZE + CELL_SIZE/2);
        gc.rotate(rotation);
        gc.drawImage(headImage, -CELL_SIZE/2, -CELL_SIZE/2, CELL_SIZE, CELL_SIZE);
        gc.restore();

        // Отрисовка препятствий для уровня 2
        if (currentLevel == 2) {
            gc.setFill(Color.RED);
            gameLogic.getObstacles().forEach(obstacle ->
                    gc.fillRect(obstacle.getX() * CELL_SIZE, obstacle.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            );
        }

        // Отрисовка еды
        FoodManager.Food food = gameLogic.getFoodManager().getCurrentFood();
        if (food != null) {
            double size = CELL_SIZE;
            double offset = 0;

            if (food.type() == FruitType.PINEAPPLE) {
                size = CELL_SIZE * 1.3; // Увеличиваем размер на 30%
                offset = (CELL_SIZE - size) / 2; // Центрируем
            }

            gc.drawImage(fruitImages.get(food.type()),
                    food.position().getX() * CELL_SIZE + offset,
                    food.position().getY() * CELL_SIZE + offset,
                    size, size
            );
        }
    }

    private void showGameOver() {
        try {
            // Проверяем, что сцена существует
            if (gameCanvas.getScene() == null) {
                System.err.println("Canvas не привязан к сцене!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game_over.fxml"));
            Parent root = loader.load();

            GameOverController controller = loader.getController();
            Stage stage = (Stage) gameCanvas.getScene().getWindow(); // Безопасно, так как проверка выше
            controller.initialize(stage, gameLogic.getScore(), currentLevel, false);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void update() {
        if (!gameStarted) return; // Не обновляем состояние игры до старта

        if (gameLogic.isGameOver()) {
            showGameOver();
            gameLoop.stop();
        }
        else if (gameLogic.isGameWon()) {
            showVictory();
            gameLoop.stop();
        }
        else {
            gameLogic.update();
        }
    }

    private void showVictory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game_over.fxml"));
            Parent root = loader.load();
            GameOverController controller = loader.getController();
            Stage stage = (Stage) gameCanvas.getScene().getWindow();
            controller.initialize(stage, gameLogic.getScore(), currentLevel, true);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (!gameStarted && event.getCode() == KeyCode.SPACE) {
            gameStarted = true;
            startText.setVisible(false);
            gameLoop.start();
            return;
        }

        Direction newDirection = switch (event.getCode()) {
            case UP -> Direction.UP;
            case DOWN -> Direction.DOWN;
            case LEFT -> Direction.LEFT;
            case RIGHT -> Direction.RIGHT;
            default -> null;
        };
        if (newDirection != null) gameLogic.getSnake().setDirection(newDirection);
    }
}
