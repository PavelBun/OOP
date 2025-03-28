// FoodManagerTest.java
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
        assertEquals(1, foodManager.getFood().size());

        Point2D food = foodManager.getFood().get(0);
        assertFalse(snake.getBody().contains(food));
    }

    @Test
    void testFoodConsumption() {
        foodManager.generateFood(snake);
        Point2D food = foodManager.getFood().get(0);

        assertTrue(foodManager.checkFoodConsumption(food));
        assertEquals(0, foodManager.getFood().size());
    }
}