package org.example;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodManager {
    private List<Point2D> food;
    private int boardWidth;
    private int boardHeight;
    private Random random;

    public FoodManager(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.food = new ArrayList<>();
        this.random = new Random();
    }

    public void generateFood(Snake snake) {
        Point2D newFood;
        do {
            newFood = new Point2D(
                    random.nextInt(boardWidth),
                    random.nextInt(boardHeight)
            );
        } while (snake.getBody().contains(newFood) || food.contains(newFood));
        food.add(newFood);
    }

    public List<Point2D> getFood() {
        return food;
    }

    public boolean checkFoodConsumption(Point2D head) {
        return food.remove(head);
    }
}