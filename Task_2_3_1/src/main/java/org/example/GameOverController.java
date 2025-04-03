package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class GameOverController {
    @FXML private Text gameOverText;
    @FXML private Text scoreText;
    private Stage stage;
    private int score;
    private int currentLevel;
    private boolean isVictory;

    public void initialize(Stage stage, int score, int level, boolean isVictory) {
        this.stage = stage;
        this.score = score;
        this.currentLevel = level;
        this.isVictory = isVictory;

        gameOverText.setText(isVictory ? "Победа!" : "Вы проиграли!");
        scoreText.setText("Счёт: " + score);
    }

    @FXML
    private void restartGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
            Parent root = loader.load();
            GameController gameController = loader.getController();
            gameController.setLevel(currentLevel);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void returnToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/start_menu.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}