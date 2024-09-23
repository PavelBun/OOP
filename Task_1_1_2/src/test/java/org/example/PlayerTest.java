package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Cards aceCard;
    private Cards kingCard;

    @BeforeEach
    void setUp() {
        player = new Player(false);
        aceCard = new Cards("Черви", "Туз", 11);
        kingCard = new Cards("Пики", "Король", 10);
    }

    @Test
    void testAddCard() {
        player.addCard(aceCard);
        assertEquals(1, player.getHand().size());
        assertEquals(11, player.calculateHandValue());
    }

    @Test
    void testClearHand() {
        player.addCard(aceCard);
        player.clearHand();
        assertEquals(0, player.getHand().size());
    }

    @Test
    void testCalculateHandValue() {
        player.addCard(aceCard);
        player.addCard(kingCard);
        assertEquals(21, player.calculateHandValue());
    }

    @Test
    void testIsBusted() {
        player.addCard(new Cards("Пики", "10", 10));
        player.addCard(new Cards("Бубны", "Дама", 10));
        player.addCard(new Cards("Черви", "Восьмерка", 8));
        assertTrue(player.isBusted());
    }

    @Test
    void testDisplayHand() {
        player.addCard(aceCard);
        player.addCard(kingCard);
        assertEquals("[Туз Черви, Король Пики] > 21", player.displayHand(true));
    }
}