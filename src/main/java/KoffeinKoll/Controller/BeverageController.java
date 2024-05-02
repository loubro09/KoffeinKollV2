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

    //ska tas bort / ändras
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
        return 0; //If there's an error, we default to 0
    }

    /**
     * Validates the amount of a beverage.
     * @param text The text representing the amount of the beverage.
     * @return True if the amount is valid, false otherwise.
     * @author                                                                                          //AUTHOR
     */
    public boolean validateAmount(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
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
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime.parse(text, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Inserts a new user history entry into the database.
     * @param userId     The ID of the user.
     * @param beverageId The ID of the beverage.
     * @param date       The date of consumption.
     *                                                                              //param mängd dryck
     * @return True if the insertion was successful, false otherwise.
     * @author                                                                                          //AUTHOR
     */
    public boolean insertUserHistory(int userId, int beverageId, LocalDate date) {
                                                                                        //ÄNDRA HÄMTA USER ID
        int newId = getMaxUserHistoryId() + 1;

        String sql = "INSERT INTO userhistory (userhistory_id, user_id, beverage_id, date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newId); // Set the new unique ID
            pstmt.setInt(2, userId);
            pstmt.setInt(3, beverageId);
            pstmt.setDate(4, java.sql.Date.valueOf(date));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        /*
        int newId = getMaxId("userhistory", "userhistory_id") + 1;
        String sql = "INSERT INTO userhistory (userhistory_id, user_id, beverage_id, date) VALUES (?, ?, ?, ?)";

        return executeUpdate(sql, newId, userId, beverageId, Date.valueOf(date));
         */
    }
}


