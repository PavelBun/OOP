package org.example;

import javafx.geometry.Point2D;

public class GameLogic {
    private Snake snake;
    private FoodManager foodManager;
    private int boardWidth;
    private int boardHeight;
    private int score;
    private int targetLength;
    private boolean gameOver;

    public GameLogic(int boardWidth, int boardHeight, int targetLength) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.targetLength = targetLength;
        snake = new Snake(new Point2D(boardWidth / 2, boardHeight / 2));
        foodManager = new FoodManager(boardWidth, boardHeight);
        foodManager.generateFood(snake);
        score = 0;
        gameOver = false;
    }

    public void update() {
        Point2D newHead = calculateNewHead();
        if (isCollision(newHead)) {
            gameOver = true;
            // Обработка столкновения
            return;
        }
        if (foodManager.checkFoodConsumption(newHead)) {
            snake.grow(newHead);
            score += 10;
            foodManager.generateFood(snake);
        } else {
            snake.move(newHead);
        }
    }

    private Point2D calculateNewHead() {
        Point2D head = snake.getHead();
        return switch (snake.getDirection()) {
            case UP -> new Point2D(head.getX(), head.getY() - 1);
            case DOWN -> new Point2D(head.getX(), head.getY() + 1);
            case LEFT -> new Point2D(head.getX() - 1, head.getY());
            case RIGHT -> new Point2D(head.getX() + 1, head.getY());
        };
    }

    private boolean isCollision(Point2D newHead) {
        return newHead.getX() < 0 || newHead.getX() >= boardWidth ||
                newHead.getY() < 0 || newHead.getY() >= boardHeight ||
                snake.getBody().subList(1, snake.getBody().size()).contains(newHead); // Исключаем голову из проверки
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
    public int getScore(){
        return score;
    }
}