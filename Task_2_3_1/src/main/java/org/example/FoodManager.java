package org.example;

import javafx.geometry.Point2D;
import java.util.*;

public class FoodManager {
    private static final Random random = new Random();
    private Food currentFood;
    private final int boardWidth;
    private final int boardHeight;

    public FoodManager(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public void generateFood(Snake snake) {
        List<Point2D> forbidden = new ArrayList<>(snake.getBody());
        if (currentFood != null) forbidden.add(currentFood.position());

        Point2D newPos;
        do {
            newPos = new Point2D(
                    random.nextInt(boardWidth),
                    random.nextInt(boardHeight)
            );
        } while (forbidden.contains(newPos));

        // Случайный выбор типа фрукта
        FruitType[] types = FruitType.values();
        currentFood = new Food(newPos, types[random.nextInt(types.length)]);
    }

    public Food getCurrentFood() {
        return currentFood;
    }
    public boolean checkFoodConsumption(Point2D headPosition) {
        if (currentFood != null && currentFood.position().equals(headPosition)) {
            currentFood = null;
            return true;
        }
        return false;
    }

    public record Food(Point2D position, FruitType type) {}
}