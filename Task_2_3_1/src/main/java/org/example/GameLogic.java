package org.example;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private Snake snake;
    private FoodManager foodManager;
    private int boardWidth;
    private int boardHeight;
    private int score;
    private int targetLength;
    private boolean gameOver;
    private List<Point2D> obstacles; // Список препятствий
    private int currentLevel;
    private long speedEffectEndTime = 0;
    private long updateInterval = 150_000_000;
    private long baseUpdateInterval = 150_000_000;
    // Начальная скорость
    public GameLogic(int boardWidth, int boardHeight, int targetLength, int level) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.targetLength = targetLength;
        snake = new Snake(new Point2D(boardWidth / 2, boardHeight / 2));
        foodManager = new FoodManager(boardWidth, boardHeight);
        foodManager.generateFood(snake);
        score = 0;
        gameOver = false;
        this.currentLevel = level;
        this.obstacles = new ArrayList<>();
        if (level == 2) {
            generateObstacles();
        }
    }

    public void update() {
        if (System.nanoTime() > speedEffectEndTime) {
            updateInterval = baseUpdateInterval;
        }
        snake.update();
        Point2D head = snake.getHead();

        if (isCollision(head)) {
            gameOver = true;
            return;
        }

        FoodManager.Food food = foodManager.getCurrentFood();
        if (food != null && food.position().equals(head)) {
            applyFoodEffect(food.type());
            foodManager.generateFood(snake);
        }
    }
    private void applyFoodEffect(FruitType type) {
        score += 10;
        snake.growBy(type.getGrowth());

        if (type == FruitType.COCONUT) {
            // Устанавливаем скорость на 2 секунды
            updateInterval = (long)(baseUpdateInterval * type.getSpeedMultiplier());
            speedEffectEndTime = System.nanoTime() + 2_000_000_000L;
        } else {
            updateInterval = baseUpdateInterval;
        }
    }
    public long getUpdateInterval() {
        return updateInterval;
    }
    private void generateObstacles() {
        // Пример препятствий в пределах доски 17x15
        obstacles.add(new Point2D(5, 5));
        obstacles.add(new Point2D(10, 7));
        obstacles.add(new Point2D(15, 10)); // X=15 (допустимо при ширине 17)
    }
    private boolean isCollision(Point2D newHead) {
        return
                // Проверка границ и тела змейки
                newHead.getX() < 0 || newHead.getX() >= boardWidth ||
                        newHead.getY() < 0 || newHead.getY() >= boardHeight ||
                        snake.getBody().subList(1, snake.getBody().size()).contains(newHead) ||
                        obstacles.contains(newHead); // Проверка препятствий
    }

    public List<Point2D> getObstacles() {
        return obstacles;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return snake.getBody().size() >= targetLength;
    }

    public Snake getSnake() {
        return snake;
    }

    public FoodManager getFoodManager() {
        return foodManager;
    }

    public int getScore() {
        return score;
    }
}
