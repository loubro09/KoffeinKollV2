package KoffeinKoll.Controller;

import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class BeverageController {
    public boolean validateAmount(String text) {
        try {
            double amount = Double.parseDouble(text);
            return amount >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*public boolean validateDateTime(String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime.parse(text, formatter);
            return Optional.empty(); // No error, validation passed
        } catch (DateTimeParseException e) {
            return Optional.of("Invalid date and time format. Please use yyyy-MM-dd HH:mm.");
        }
    }*/

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


