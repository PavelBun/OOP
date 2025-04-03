package org.example;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    private GameLogic gameLogic;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic(20, 20, 10, 1);
    }

    @Test
    void testInitialState() {
        assertFalse(gameLogic.isGameOver());
        assertEquals(0, gameLogic.getScore());
    }

    @Test
    void testCollisionWithWall() {
        // Двигаем змейку до границы
        for (int i = 0; i < 20; i++) {
            gameLogic.getSnake().setDirection(Direction.RIGHT);
            gameLogic.update();
        }
        assertTrue(gameLogic.isGameOver());
    }

    @Test
    void testSelfCollision() {
        Snake snake = gameLogic.getSnake();

        // Форсируем рост змейки
        snake.growBy(4);

        // Создаем петлю
        snake.setDirection(Direction.UP);
        gameLogic.update(); // Движение вверх
        snake.setDirection(Direction.LEFT);
        gameLogic.update(); // Движение влево
        snake.setDirection(Direction.DOWN);
        gameLogic.update();
        snake.setDirection(Direction.RIGHT);
        gameLogic.update();

        assertTrue(gameLogic.isGameOver(), "Змейка должна столкнуться сама с собой");
    }
}