package org.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Cards> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Черви", "Бубны", "Трефы", "Пики"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Валет", "Дама", "Король", "Туз"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Cards(suit, ranks[i], values[i]));
            }
        }
        shuffle(); // Перемешиваем колоду при создании
    }

    // Метод для перемешивания карт
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Метод для вытягивания карты
    public Cards drawCard() {
        return cards.remove(0); // Убираем карту из колоды и возвращаем её
    }

    // метод который возвращает список всех карт
    public List<Cards> getCards() {
        return cards;
    }
}
