module com.example.koffeinkollv2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.koffeinkollv2 to javafx.fxml;
    exports com.example.koffeinkollv2;
}