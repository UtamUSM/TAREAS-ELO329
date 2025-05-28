module com.example.stage2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stage2 to javafx.fxml;
    exports com.example.stage2;
}