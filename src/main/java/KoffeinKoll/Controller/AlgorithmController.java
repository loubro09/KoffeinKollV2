package KoffeinKoll.Controller;

import KoffeinKoll.View.CustomGauge;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class AlgorithmController {
    private DatabaseConnection databaseConnection;

    public AlgorithmController() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }


    public double calculateTime(double c0) {
        double cF = 1; // mg
        // hours
        double halfLife = 5.7;
        return -halfLife * Math.log(cF / c0) / Math.log(2);
    }

    public void updateGaugeNewLog(int beverageId, double beverageAmount) {
        double caffeine = getBeverageConcentration(beverageId) * beverageAmount;
        double newDrinkTime = calculateTime(caffeine);
        CustomGauge cg = CustomGauge.getInstance();
        double currentDrinkTime = cg.getCurrentValue();
        newDrinkTime += currentDrinkTime;
        cg.changeValue((int) newDrinkTime);

        String sql = "UPDATE users SET current_max_gauge_time = ? WHERE user_id = ?";

        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Set parameters for the PreparedStatement
            stmt.setDouble(1, newDrinkTime);
            stmt.setInt(2, UserController.getInstance().getId());

            // Execute the update
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("currentMaxGaugeTime updated successfully for user with ID ");
            } else {
                System.out.println("Failed to update currentMaxGaugeTime for user with ID ");
            }
        } catch (SQLException e) {
            System.err.println("Error updating currentMaxGaugeTime: " + e.getMessage());
        }
    }

    public double currentGaugeValue() {
        double currentMax = getMaxValue();
        LocalTime lastLogTime = lastLog();
        long hoursSinceLastLog;
        if (lastLogTime == null) {
            hoursSinceLastLog = 0;
        }
        else {
            LocalTime currentTime = LocalTime.now();
            hoursSinceLastLog = ChronoUnit.HOURS.between(lastLogTime, currentTime);
        }

        return currentMax - hoursSinceLastLog;
    }

    public double getMaxValue() {
        String sql = "SELECT current_max_gauge_time FROM users WHERE user_id = ?";

        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Set the parameter for the PreparedStatement
            stmt.setInt(1, UserController.getInstance().getId());

            // Execute the query
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Retrieve the value from the result set
                return rs.getInt("current_max_gauge_time");
            } else {
                // No row found for the specified user
                System.out.println("No currentMaxGaugeTime found for user ");
                return 0; // or handle the absence of data according to your application's logic
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving currentMaxGaugeTime: " + e.getMessage());
            return 0; // or handle the error according to your application's logic
        }
    }

    private LocalTime lastLog() {
            // SQL query to retrieve the time of the last added row for the specified user and today's date
            String sql = "SELECT date AS lastAddedTime FROM userhistory WHERE user_id = ? AND DATE(date) = ? ORDER BY date DESC LIMIT 1";
            try (
                    Connection conn = DatabaseConnection.getInstance().getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql)
            ) {
                // Set parameters for the PreparedStatement
                stmt.setInt(1, UserController.getInstance().getId());
                stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));

                // Execute the query
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Retrieve the time from the result set
                    return rs.getTime("lastAddedTime").toLocalTime();
                } else {
                    // No rows found matching the criteria
                    System.out.println("No rows found for user " + " today.");
                    return null;
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving last added row time: " + e.getMessage());
                return null;
            }
    }

    public double getBeverageConcentration(int beverageId){
        double concentration = 0.0;
        String query= "SELECT caffeine_concentration FROM beverages WHERE beverage_id = ?";

        try (Connection conn = databaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, beverageId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                concentration = resultSet.getDouble("caffeine_concentration");
            }else{
                System.out.println("beverage cannot be found in database");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return concentration;
    }

    public double getTotalCaffeineForDay(int userId) {
        double totalCaffeine = 0;
        String query = "SELECT SUM(b.caffeine_concentration * uh.amount) AS totalCaffeine " +
                "FROM userhistory uh " +
                "JOIN users u ON uh.user_id = u.user_id " +
                "JOIN beverages b ON uh.beverage_id = b.beverage_id " +
                "WHERE uh.user_id = ? AND DATE(uh.date) = ?";

        try (Connection conn = databaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalCaffeine = rs.getDouble("totalCaffeine");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return calculateTime(totalCaffeine);
    }
}
