package org.example;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodManagerTest {
    private FoodManager foodManager;
    private Snake snake;

    @BeforeEach
    void setUp() {
        foodManager = new FoodManager(20, 20);
        snake = new Snake(new Point2D(10, 10));
    }

    @Test
    void testFoodGeneration() {
        foodManager.generateFood(snake);
        assertNotNull(foodManager.getCurrentFood());

        Point2D foodPos = foodManager.getCurrentFood().position();
        assertFalse(snake.getBody().contains(foodPos));
    }

    @Test
    void testFoodConsumption() {
        foodManager.generateFood(snake);
        Point2D foodPos = foodManager.getCurrentFood().position();

        // Корректное потребление
        assertTrue(foodManager.checkFoodConsumption(foodPos));
        assertNull(foodManager.getCurrentFood());

        // Попытка потребления несуществующей еды
        assertFalse(foodManager.checkFoodConsumption(new Point2D(0, 0)));
    }
}