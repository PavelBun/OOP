module org.example.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    opens org.example to javafx.fxml, com.fasterxml.jackson.databind;
    exports org.example;
}