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
    void testPlayRound() throws Exception {
        // Проверка, что раунд играется без ошибок
        try (Scanner scanner = new Scanner("0\n")) { // Игрок сразу останавливается
            assertDoesNotThrow(() -> blackJack.playRound(scanner));
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    void testDealerTurn() throws Exception {
        // Проверка, что дилер берет карты до 17
        try (Scanner scanner = new Scanner("0\n")) { // Игрок сразу останавливается
            blackJack.playRound(scanner);
            int dealerValue = blackJack.dealer.calculateHandValue();
            assertTrue(dealerValue >= 17);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    void testDetermineWinner() throws Exception {
        // Проверка определения победителя
        try(Scanner scanner = new Scanner("0\n")) { // Игрок сразу останавливается
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
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    void testPlayerBusted() throws Exception {
        // Проверка, что игрок проигрывает, если его очки больше 21
        try(Scanner scanner = new Scanner("1\n1\n1\n1\n1\n1\n")) {
            blackJack.playRound(scanner);
            assertTrue(blackJack.player.isBusted());
            assertEquals(1, blackJack.dealerScore);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    void testDealerBusted() throws Exception {
        // Проверка, что дилер проигрывает, если его очки больше 21
        try(Scanner scanner = new Scanner("0\n")) {
            blackJack.playRound(scanner);
            if (blackJack.dealer.isBusted()) {
                assertEquals(1, blackJack.playerScore);
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    void testBlackJack() throws Exception {
        // Проверка, что игрок выигрывает с блэкджеком
        try(Scanner scanner = new Scanner("0\n")) {
            blackJack.playRound(scanner);
            if (blackJack.player.isBlackJack() && !blackJack.dealer.isBlackJack()) {
                assertEquals(1, blackJack.playerScore);
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    void testDealerBlackJack() throws Exception {
        // Проверка, что дилер выигрывает с блэкджеком
        try(Scanner scanner = new Scanner("0\n")) {
            blackJack.playRound(scanner);
            if (blackJack.dealer.isBlackJack() && !blackJack.player.isBlackJack()) {
                assertEquals(1, blackJack.dealerScore);
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    void testDraw() throws Exception {
        // Проверка ничьи
        try (Scanner scanner = new Scanner("0\n")) {
            blackJack.playRound(scanner);
            if (blackJack.player.calculateHandValue() == blackJack.dealer.calculateHandValue()) {
                assertEquals(0, blackJack.playerScore);
                assertEquals(0, blackJack.dealerScore);
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}