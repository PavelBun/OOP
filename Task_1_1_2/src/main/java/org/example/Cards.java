package org.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Класс Card
class Cards {
    private String suit;
    private String rank;
    private int value;

    public Cards(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return rank + " " + suit;
    }
}