module org.example.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;

    opens org.example to javafx.fxml;
    exports org.example;
}