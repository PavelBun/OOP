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

    public void initialize(Stage stage, int score) {
        this.stage = stage;
        this.score = score;
        gameOverText.setText("Вы проиграли!");
        scoreText.setText("Счёт: " + score);
    }

    @FXML
    private void restartGame() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}