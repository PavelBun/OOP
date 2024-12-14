package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void testCardCreation() {
        Cards card = new Cards("Черви", "Туз", 11);
        assertEquals("Черви", card.getSuit());
        assertEquals("Туз", card.getRank());
        assertEquals(11, card.getValue());
    }

    @Test
    void testCardToString() {
        Cards card = new Cards("Пики", "Дама", 10);
        assertEquals("Дама Пики", card.toString());
    }
}
