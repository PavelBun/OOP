package org.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
class Player {
    private List<Cards> hand;
    private boolean isDealer;

    public Player(boolean isDealer) {
        this.hand = new ArrayList<>();
        this.isDealer = isDealer;
    }

    public void addCard(Cards card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear(); // Очистка руки
    }

    public int calculateHandValue() {
        int value = 0;
        int aceCount = 0;

        for (Cards card : hand) {
            value += card.getValue();
            if (card.getRank().equals("Туз")) {
                aceCount++;
            }
        }

        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }

        return value;
    }

    public boolean isBusted() {
        return calculateHandValue() > 21;
    }

    public List<Cards> getHand() {
        return hand;
    }

    public String displayHand(boolean showAllCards) {
        if (isDealer && !showAllCards) {
            return hand.get(0) + ", <закрытая карта>";
        }
        return hand.toString() + " => " + calculateHandValue();
    }
}
