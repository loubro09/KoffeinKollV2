package KoffeinKoll.Controller;

import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class BeverageController {

    private DatabaseConnection databaseConnection;

    public boolean validateAmount(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false; // Null or empty string is considered invalid
        }
        try {
            double amount = Double.parseDouble(text);
            return amount >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validateDateTime(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false; // Null or empty string is considered invalid
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime.parse(text, formatter);
            return true; // No error, validation passed
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean insertUserHistory(int userId, int beverageId, LocalDate date) {
        String sql = "INSERT INTO userhistory (user_id, beverage_id, date) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, beverageId);
            pstmt.setDate(3, java.sql.Date.valueOf(date));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


