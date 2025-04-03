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
        snake.setDirection(Direction.RIGHT);
        snake.update();
        assertEquals(new Point2D(6, 5), snake.getHead());
    }

    @Test
    void testGrowth() {
        snake.growBy(2);
        for (int i = 0; i < 3; i++) snake.update();
        assertEquals(3, snake.getBody().size());
    }


}