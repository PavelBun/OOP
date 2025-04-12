package org.example;

import javafx.application.Platform;
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
    private GameThread gameThread;
    private int levelId;




    // Установка уровня из меню выбора
    public void setLevel(int levelId) {
        try {
            LevelConfig config = LevelLoader.loadLevel(levelId);
            gameLogic = new GameLogic(levelId);
            currentLevel = levelId;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load level", e);
        }
    }


    // Удаляем AnimationTimer и добавляем методы для управления потоком
    public void startGameLoop() {
        gameThread = new GameThread(this, gameLogic.getUpdateInterval());
        gameThread.start();
    }

    // GameController.java
    public void stopGameLoop() {
        if (gameThread != null) {
            gameThread.stopGame(); // Неблокирующая остановка
            gameThread = null;
        }
    }

    // Обновление метода update в GameController
    private boolean gameOverHandled = false;  // Флаг, чтобы не запускать завершение несколько раз

    public boolean updateGameState() {
        // Проверяем, завершена ли игра
        if (gameLogic.isGameOver() || gameLogic.isGameWon()) {
            // Если игра закончена, останавливаем игровой поток и показываем окно окончания игры
            stopGameLoop();  // Останавливаем игровой поток
            showGameOver();   // Показываем экран Game Over или Victory
            return false;     // Прекращаем обновление игры
        }

        // Если игра не завершена, продолжаем обновлять состояние игры
        gameLogic.update();
        return true;  // Игра продолжается
    }

    @FXML
    public void initialize() throws Exception {
        backgroundImage = new Image(getClass().getResourceAsStream("/images/grass.png"));
        for (FruitType type : FruitType.values()) {
            fruitImages.put(type, new Image(getClass().getResourceAsStream(type.getImagePath())));
        }
        headImage = new Image(getClass().getResourceAsStream("/images/snake_head.png"));
        tailImage = new Image(getClass().getResourceAsStream("/images/snake_tail.png"));
        gc = gameCanvas.getGraphicsContext2D();
        gameLogic = new GameLogic(currentLevel);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();

        gameCanvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::handleKeyPress);
            }
        });

        gameCanvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::handleKeyPress);
            }
        });

        startText.setVisible(true);


    }

    void render() {
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
    private void update() {
        if (!gameStarted) return;

        if (gameLogic.isGameOver()) {
            System.out.println("[DEBUG] Game Over detected");
            showGameOver(); // Убрать Platform.runLater()
        } else if (gameLogic.isGameWon()) {
            System.out.println("[DEBUG] Victory detected");
            showVictory();
        } else {
            gameLogic.update();
        }
    }


    private void showGameOver() {
        Platform.runLater(() -> {
            try {
                if (gameCanvas.getScene() == null) {
                    System.out.println("[ERROR] Scene is null");
                    return;
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/game_over.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) gameCanvas.getScene().getWindow();
                GameOverController controller = loader.getController();
                controller.initialize(stage, gameLogic.getScore(), currentLevel, false);
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                System.err.println("[CRITICAL] Failed to load game_over.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    private void showVictory() {
        stopGameLoop();
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
            startGameLoop(); // Запускаем игровой поток
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
