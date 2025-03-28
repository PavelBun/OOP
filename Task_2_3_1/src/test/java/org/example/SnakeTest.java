// SnakeTest.java
package org.example;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {
    private Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake(new Point2D(5, 5));
    }

    @Test
    void testInitialState() {
        assertEquals(1, snake.getBody().size());
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    void testMovement() {
        snake.move(new Point2D(6, 5));
        assertEquals(new Point2D(6, 5), snake.getHead());
        assertEquals(1, snake.getBody().size());
    }

    @Test
    void testGrowth() {
        snake.grow(new Point2D(6, 5));
        assertEquals(2, snake.getBody().size());
        assertEquals(new Point2D(6, 5), snake.getBody().get(0));
    }

    @Test
    void testDirectionChange() {
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getDirection());

        // Проверка блокировки противоположного направления
        snake.setDirection(Direction.DOWN);
        assertEquals(Direction.UP, snake.getDirection());
    }
}