package org.example;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private static final int BOARD_WIDTH = 17; // Фиксированные размеры
    private static final int BOARD_HEIGHT = 15;
    private static final long BASE_INTERVAL_NS = 200_000_000;
    private long updateInterval = BASE_INTERVAL_NS;
    private Snake snake;
    private FoodManager foodManager;
    private int score;
    private int targetLength;
    private boolean gameOver;
    private List<Point2D> obstacles;
    private int currentLevel;
    private long speedEffectEndTime = 0;
    private long baseUpdateInterval = 200_000_000;

    public GameLogic(int targetLength, int level) throws Exception {
        LevelConfig config = LevelLoader.loadLevel(level);
        this.targetLength = config.getTargetLength();
        snake = new Snake(new Point2D(BOARD_WIDTH / 2, BOARD_HEIGHT / 2));
        foodManager = new FoodManager(BOARD_WIDTH, BOARD_HEIGHT);
        foodManager.generateFood(snake);
        score = 0;
        gameOver = false;
        this.currentLevel = level;
        this.obstacles = new ArrayList<>(config.getObstacles());
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

    private boolean isCollision(Point2D newHead) {
        boolean outOfBounds = newHead.getX() < 0 || newHead.getX() >= BOARD_WIDTH
                || newHead.getY() < 0 || newHead.getY() >= BOARD_HEIGHT;
        boolean selfCollision = snake.getBody().subList(1, snake.getBody().size()).contains(newHead);
        boolean obstacleCollision = obstacles.contains(newHead);
        return outOfBounds || selfCollision || obstacleCollision;
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

    public long getUpdateInterval() {
        return updateInterval;
    }
}
