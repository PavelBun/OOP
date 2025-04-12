package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class StartMenuController {
    @FXML
    private void startGame(ActionEvent event) throws IOException { // Добавьте параметр ActionEvent
        // Получаем Stage из источника события (кнопки)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/level_selection.fxml"));
        Parent root = loader.load();
        LevelSelectionController controller = loader.getController();
        controller.setStage(stage); // Передаем текущий Stage

        stage.setScene(new Scene(root));
    }

    @FXML
    private void exitGame() {
        System.exit(0);
    }
}