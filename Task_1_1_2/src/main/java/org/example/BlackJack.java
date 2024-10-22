package org.example;
import java.util.Scanner;

// Класс игры
public class BlackJack {
    private final Deck deck;
    public final Player player;
    public final Player dealer;
    public int playerScore;
    public int dealerScore;

    public BlackJack() {
        deck = new Deck();
        player = new Player(false);
        dealer = new Player(true);
        playerScore = 0;
        dealerScore = 0;
    }

    public void startGame() {
        System.out.println("Добро пожаловать в Блэкджек!");

        Scanner scanner = new Scanner(System.in);
        int round = 1;

        while (true) {
            System.out.println("\nРаунд " + round);
            playRound(scanner);

            System.out.println("Хотите сыграть еще? (yes/no)");

            if (!scanner.next().equalsIgnoreCase("yes")) {
                break;
            }

            round++;
        }

        System.out.println("Игра окончена. Счет " + playerScore + ":" + dealerScore);
        scanner.close();
    }

    public void playRound(Scanner scanner) {
        // Очистка рук для нового раунда
        player.clearHand();
        dealer.clearHand();

        // Раздача карт
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        System.out.println("Дилер раздал карты");
        System.out.println("Ваши карты: " + player.displayHand(true));
        System.out.println("Карты дилера: [" + dealer.displayHand(false) + "]");

        // Ход игрока
        if (!player.isBlackJack()) {
            playerTurn(scanner);
        }
        if (player.isBusted()) {
            System.out.println("Вы проиграли! Превышен лимит в 21 очко.");
            dealerScore++;
            return;
        }


        // Ход дилера
        dealerTurn();

        if (player.isBlackJack() && !dealer.isBlackJack()) {
            System.out.println("Вы выиграли раунд!");
            playerScore ++;
            return;
        }
        else if (dealer.isBlackJack() && !player.isBlackJack()) {
            System.out.println("Дилер выиграл раунд!");
            dealerScore++;
            return;
        }

        // Итоги раунда
        determineWinner();
    }

    private void playerTurn(Scanner scanner) {
        while (true) {
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться.");
            int choice = scanner.nextInt();

            if (choice == 1) {
                player.addCard(deck.drawCard());
                System.out.println("Ваши карты: " + player.displayHand(true));
                if (player.isBusted()) {
                    return;
                }
                if (player.isBlackJack()){
                    return;
                }
            } else {
                break;
            }
        }
    }

    private void dealerTurn() {
        System.out.println("\nХод дилера");
        System.out.println("Карты дилера: [" + dealer.displayHand(true) + "]");

        while (dealer.calculateHandValue() < 17) {
            dealer.addCard(deck.drawCard());
            System.out.println("Дилер открывает карту: " + dealer.getHand().get(dealer.getHand().size() - 1));
            System.out.println("Карты дилера: ["  + dealer.displayHand(true) + "]");
        }

        if (dealer.isBusted()) {
            System.out.println("Дилер проиграл! Превышен лимит в 21 очко.");
        }
    }

    public void determineWinner() {
        int playerValue = player.calculateHandValue();
        int dealerValue = dealer.calculateHandValue();

        if (dealer.isBusted() || (playerValue > dealerValue && playerValue <= 21)) {
            System.out.println("Вы выиграли раунд!");
            playerScore++;
        } else if (playerValue == dealerValue) {
            System.out.println("Ничья!");
        } else {
            System.out.println("Дилер выиграл раунд!");
            dealerScore++;
        }
    }


    public static void main(String[] args) {
        BlackJack game = new BlackJack();
        game.startGame();
    }
}