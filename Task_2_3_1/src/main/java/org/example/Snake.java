package org.example;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;

public class Snake {
    private List<Point2D> body;
    private Direction currentDirection;
    private Direction nextDirection;
    private int pendingGrowth;

    public Snake(Point2D startPosition) {
        body = new ArrayList<>();
        body.add(startPosition);
        currentDirection = Direction.RIGHT;
        nextDirection = currentDirection;
    }

    public void setDirection(Direction newDirection) {
        // Запрещаем противоположное направление
        if (newDirection != currentDirection.opposite()) {
            nextDirection = newDirection;
        }
    }

    public void update() {
        currentDirection = nextDirection;
        Point2D newHead = calculateNewHead();
        body.add(0, newHead);

        // Добавляем новые сегменты в хвост
        if (pendingGrowth > 0) {
            pendingGrowth--;
        } else {
            body.remove(body.size() - 1);
        }
    }

    private Point2D calculateNewHead() {
        Point2D head = getHead();
        return switch (currentDirection) {
            case UP -> new Point2D(head.getX(), head.getY() - 1);
            case DOWN -> new Point2D(head.getX(), head.getY() + 1);
            case LEFT -> new Point2D(head.getX() - 1, head.getY());
            case RIGHT -> new Point2D(head.getX() + 1, head.getY());
        };
    }
    public void growBy(int segments) {
        this.pendingGrowth += segments; // Накопление сегментов для роста
    }


    // Геттеры
    public Direction getDirection() {
        return currentDirection;
    }

    public Point2D getHead() {
        return body.get(0);
    }

    public List<Point2D> getBody() {
        return body;
    }

    public void grow(Point2D newHead) {
        body.add(0, newHead);
    }
}
