package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LevelSelectionController {
    private Stage currentStage;

    public void setStage(Stage stage) {
        this.currentStage = stage;
    }

    @FXML
    private void startLevel1() throws IOException {
        startGame(1);
    }

    @FXML
    private void startLevel2() throws IOException {
        startGame(2);
    }

    private void startGame(int level) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        GameController gameController = loader.getController();
        gameController.setLevel(level);

        // Используем сохраненный Stage
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }
}