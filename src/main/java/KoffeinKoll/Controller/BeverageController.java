package KoffeinKoll.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
            System.out.println("Error in getMaxUserHistoryId" + e.getMessage());
        }
        return 0;
    }

    /**
     * Validates the amount of a beverage.
     * @param text The text representing the amount of the beverage.
     * @return True if the amount is valid, false otherwise.
     * @author Ida Nordenswan                                                                                         //AUTHOR
     */
    public boolean validateAmount(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        try {
            double amount = Double.parseDouble(text);
            return amount >= 0;
        } catch (NumberFormatException e) {
            System.out.println("Error in validateAmount: " + e.getMessage());
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
        int newId = getMaxUserHistoryId() + 1;

        String sql = "INSERT INTO userhistory (userhistory_id, user_id, beverage_id, date, amount) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, beverageId);
            if (dateTime != null) {
                pstmt.setObject(4,dateTime);
            }
            else {
                pstmt.setObject(4, LocalDateTime.now());
            }
            pstmt.setDouble(5, amount);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in insertUserHistory: " + e.getMessage());
            return false;
        }
    }
}