package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class BlackJackTest {

    private BlackJack game;
    private Player player;
    private Player dealer;

    @BeforeEach
    void setUp() {
        game = new BlackJack();
        player = new Player(false);
        dealer = new Player(true);
    }

    @Test
    void testPlayerWinsWhenDealerBusts() {
        player.addCard(new Cards("Черви", "10", 10));
        player.addCard(new Cards("Бубны", "Дама", 10));
        dealer.addCard(new Cards("Пики", "10", 10));
        dealer.addCard(new Cards("Пики", "Дама", 10));
        dealer.addCard(new Cards("Черви", "Восьмерка", 8));

        // Проверка, что дилер проиграл
        assertTrue(dealer.isBusted());
    }

    @Test
    void testDetermineWinnerPlayerWins() {
        player.addCard(new Cards("Черви", "10", 10));
        player.addCard(new Cards("Бубны", "Дама", 10));
        dealer.addCard(new Cards("Пики", "10", 10));
        dealer.addCard(new Cards("Пики", "7", 7));

        assertEquals(17, dealer.calculateHandValue());
        assertEquals(20, player.calculateHandValue());

    }

}
