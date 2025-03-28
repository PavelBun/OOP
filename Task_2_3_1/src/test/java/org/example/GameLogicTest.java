// GameLogicTest.java
package org.example;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    private GameLogic gameLogic;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic(20, 20, 10);
    }

    @Test
    void testInitialState() {
        assertFalse(gameLogic.isGameOver());
        assertEquals(0, gameLogic.getScore());
    }

    @Test
    void testCollisionWithWall() {
        // Перемещаем змейку к границе
        gameLogic.getSnake().move(new Point2D(19, 10));
        gameLogic.update();
        assertTrue(gameLogic.isGameOver());
    }


    @Test
    void testSelfCollision() {
        Snake snake = gameLogic.getSnake();
        snake.grow(new Point2D(11, 10));
        snake.grow(new Point2D(11, 11));
        snake.setDirection(Direction.UP);
        gameLogic.update();
        assertTrue(gameLogic.isGameOver());
    }
}