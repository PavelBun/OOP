package org.example;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;

public class Snake {
    private List<Point2D> body;
    private Direction direction;
    private Point2D head;

    public Snake(Point2D startPosition) {
        body = new ArrayList<>();
        body.add(startPosition);
        head = startPosition;
        direction = Direction.RIGHT;
    }

    public void setDirection(Direction newDirection) {
        if (!direction.equals(newDirection.opposite())) {
            direction = newDirection;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public Point2D getHead() {
        return head;
    }

    public List<Point2D> getBody() {
        return body;
    }

    public void move(Point2D newHead) {
        body.add(0, newHead);
        head = newHead;
        body.remove(body.size() - 1);
    }

    public void grow(Point2D newHead) {
        body.add(0, newHead);
        head = newHead;
    }
}