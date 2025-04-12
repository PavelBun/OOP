package org.example;

import javafx.geometry.Point2D;

import java.util.*;

public class FoodManager {
    private static final Random random = new Random();
    private Food currentFood;
    private final int boardWidth;
    private final int boardHeight;
    private Set<Point2D> availableCells;  // Множество свободных клеток

    public FoodManager(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.availableCells = new HashSet<>();
        generateAvailableCells();
    }

    // Генерация всех клеток на поле
    private void generateAvailableCells() {
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                availableCells.add(new Point2D(x, y));
            }
        }
    }

    // Генерация еды
    public void generateFood(Snake snake) {
        // Убираем клетки, занятые змейкой и текущей едой
        Set<Point2D> forbidden = new HashSet<>(snake.getBody());
        if (currentFood != null) forbidden.add(currentFood.position());

        // Оставшиеся свободные клетки
        availableCells.removeAll(forbidden);

        if (!availableCells.isEmpty()) {
            // Выбираем случайную клетку из оставшихся
            List<Point2D> availableList = new ArrayList<>(availableCells);
            Point2D newPos = availableList.get(random.nextInt(availableList.size()));

            // Случайный выбор типа фрукта
            FruitType[] types = FruitType.values();
            currentFood = new Food(newPos, types[random.nextInt(types.length)]);

            // Добавляем клетку обратно в доступные после генерации еды
            availableCells.add(newPos);
        }
    }

    // Получение текущей еды
    public Food getCurrentFood() {
        return currentFood;
    }

    // Проверка потребления еды
    public boolean checkFoodConsumption(Point2D headPosition) {
        if (currentFood != null && currentFood.position().equals(headPosition)) {
            // Убираем съеденную клетку из доступных
            availableCells.remove(currentFood.position());
            currentFood = null;
            return true;
        }
        return false;
    }

    // Запись еды
    public record Food(Point2D position, FruitType type) {}
}
