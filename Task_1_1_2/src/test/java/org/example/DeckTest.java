package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void testDeckSize() {
        // В колоде должно быть 52 карты
        assertEquals(52, deck.getCards().size());
    }

    @Test
    void testDrawCardReducesDeckSize() {
        int initialSize = deck.getCards().size();
        deck.drawCard();
        assertEquals(initialSize - 1, deck.getCards().size());
    }

    @Test
    void testDeckShuffle() {
        Deck newDeck = new Deck();
        deck.shuffle();
        // Проверяем, что после перемешивания колоды порядок карт изменяется
        assertNotEquals(deck.getCards(), newDeck.getCards());
    }
}
