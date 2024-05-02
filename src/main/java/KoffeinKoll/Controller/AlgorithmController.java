package KoffeinKoll.Controller;

import java.sql.*;
import java.time.LocalDate;

public class AlgorithmController {
    private  int beverageId;
    private double beverageAmount;
    private DatabaseConnection databaseConnection;
    private double beverageConcentration;
    double halfLife = 5.7; // hours


    public AlgorithmController() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    public double calculateTotalCaffeineForDay(int userId) {
        // Implement logic to fetch total caffeine logged for the specific day from the database
        return getTotalCaffeineForDay(userId);
    }

    public double calculateTime() {
        double cF = 1; // mg
        double c0 = beverageAmount * beverageConcentration;
        return -halfLife * Math.log(cF / c0) / Math.log(2);
    }


    public void calculateCaffeineAmount(int beverageId, double beverageAmount) {
        this.beverageId = beverageId;
        this.beverageAmount = beverageAmount;
        beverageConcentration = databaseConnection.getBeverageConcentration(beverageId);
        System.out.println("Time required: " + calculateTime() + " hours");
    }

    public double getBeverageConcentration() {
        return beverageConcentration;
    }

    public double getTotalCaffeineForDay(int userId) {
        double totalCaffeine = 0;
        String query = "SELECT SUM(b.caffeine_amount * uh.amount) AS totalCaffeine " +
                "FROM userhistory uh " +
                "JOIN users u ON uh.user_id = u.user_id " +
                "JOIN beverages b ON uh.beverage_id = b.beverage_id " +
                "WHERE uh.user_id = ? AND DATE(uh.date) = ?";

        try (Connection conn = databaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, LocalDate.now().toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalCaffeine = rs.getDouble("totalCaffeine");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return totalCaffeine;
    }
}
