module com.example.stage2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.stage2 to javafx.fxml;
    exports com.example.stage2;
}