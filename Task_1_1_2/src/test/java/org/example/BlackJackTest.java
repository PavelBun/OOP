package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class BlackJackTest {

    private BlackJack blackJack;

    @BeforeEach
    void setUp() {
        blackJack = new BlackJack();
    }

    @Test
    void testPlayRound() {
        // Проверка, что раунд играется без ошибок
        Scanner scanner = new Scanner("0\n"); // Игрок сразу останавливается
        assertDoesNotThrow(() -> blackJack.playRound(scanner));
    }

    @Test
    void testDealerTurn() {
        // Проверка, что дилер берет карты до 17
        Scanner scanner = new Scanner("0\n"); // Игрок сразу останавливается
        blackJack.playRound(scanner);
        int dealerValue = blackJack.dealer.calculateHandValue();
        assertTrue(dealerValue >= 17);
    }

    @Test
    void testDetermineWinner() {
        // Проверка определения победителя
        Scanner scanner = new Scanner("0\n"); // Игрок сразу останавливается
        blackJack.playRound(scanner);
        int playerValue = blackJack.player.calculateHandValue();
        int dealerValue = blackJack.dealer.calculateHandValue();

        if (dealerValue > 21 || (playerValue > dealerValue && playerValue <= 21)) {
            assertEquals(1, blackJack.playerScore);
        } else if (playerValue == dealerValue) {
            assertEquals(0, blackJack.playerScore);
            assertEquals(0, blackJack.dealerScore);
        } else {
            assertEquals(1, blackJack.dealerScore);
        }
    }

    @Test
    void testPlayerBusted() {
        // Проверка, что игрок проигрывает, если его очки больше 21
        Scanner scanner = new Scanner("1\n1\n1\n1\n1\n1\n");
        blackJack.playRound(scanner);
        assertTrue(blackJack.player.isBusted());
        assertEquals(1, blackJack.dealerScore);
    }

    @Test
    void testDealerBusted() {
        // Проверка, что дилер проигрывает, если его очки больше 21
        Scanner scanner = new Scanner("0\n");
        blackJack.playRound(scanner);
        if (blackJack.dealer.isBusted()) {
            assertEquals(1, blackJack.playerScore);
        }
    }

    @Test
    void testBlackJack() {
        // Проверка, что игрок выигрывает с блэкджеком
        Scanner scanner = new Scanner("0\n");
        blackJack.playRound(scanner);
        if (blackJack.player.isBlackJack() && !blackJack.dealer.isBlackJack()) {
            assertEquals(1, blackJack.playerScore);
        }
    }

    @Test
    void testDealerBlackJack() {
        // Проверка, что дилер выигрывает с блэкджеком
        Scanner scanner = new Scanner("0\n");
        blackJack.playRound(scanner);
        if (blackJack.dealer.isBlackJack() && !blackJack.player.isBlackJack()) {
            assertEquals(1, blackJack.dealerScore);
        }
    }

    @Test
    void testDraw() {
        // Проверка ничьи
        Scanner scanner = new Scanner("0\n");
        blackJack.playRound(scanner);
        if (blackJack.player.calculateHandValue() == blackJack.dealer.calculateHandValue()) {
            assertEquals(0, blackJack.playerScore);
            assertEquals(0, blackJack.dealerScore);
        }
    }
}