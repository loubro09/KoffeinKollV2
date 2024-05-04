package KoffeinKoll.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The BeverageController class handles operations related to beverage data and user history in the KoffeinKoll application.
 */
public class BeverageController {

    public int getMaxUserHistoryId() {
        String sql = "SELECT MAX(userhistory_id) AS maxId FROM userhistory";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("maxId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // If there's an error, we default to 0
    }

    /**
     * Validates the amount of a beverage.
     * @param text The text representing the amount of the beverage.
     * @return True if the amount is valid, false otherwise.
     * @author                                                                                          //AUTHOR
     */
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

    /**
     * Validates a date and time string.
     * @param text The text representing the date and time.
     * @return True if the date and time string is valid, false otherwise.
     * @author                                                                                          //AUTHOR
     */

    //TA BORT? ANVÄNDS INTE NU

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

    /**
     * Inserts a new user history entry into the database.
     * @param userId     The ID of the user.
     * @param beverageId The ID of the beverage.
     * @param dateTime       The date of consumption.
     * @param amount     The amount of beverage consumption.
     * @return True if the insertion was successful, false otherwise.
     * @author                                                                                          //AUTHOR
     */
    public boolean insertUserHistory(int userId, int beverageId, LocalDateTime dateTime, double amount) {
        // First, get the maximum ID and increment it
        int newId = getMaxUserHistoryId() + 1;

        String sql = "INSERT INTO userhistory (userhistory_id, user_id, beverage_id, date, amount) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newId); // Set the new unique ID
            pstmt.setInt(2, userId);
            pstmt.setInt(3, beverageId);
            if (dateTime != null) {
                pstmt.setObject(4,dateTime);
            }
            else {
                pstmt.setObject(4, LocalDateTime.now());
            }
            pstmt.setDouble(5, amount); // Set the amount
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the total caffeine intake for a user on the current day.
     *
     * @param userId The ID of the user.
     * @return The total caffeine intake for the user on the current day.
     * @author alanahColeman
     */
    public int getDailyCaffeineIntake(int userId) {
        int totalCaffeine = 0;

        String sql = "SELECT SUM(beverage_caffeine) AS totalCaffeine FROM userhistory " +
                "INNER JOIN beverages ON userhistory.beverage_id = beverages.beverage_id " +
                "WHERE userhistory.user_id = ? AND DATE(userhistory.date) = CURRENT_DATE";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalCaffeine = rs.getInt("totalCaffeine");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalCaffeine;
    }
}