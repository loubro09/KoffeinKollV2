package KoffeinKoll.Controller;

import KoffeinKoll.View.CustomGauge;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * The AlgorithmController class handles methods for calculating caffeine metabolism time. It also updates the gauge when a new consumed beverage is logged, and gets information needed from the database.
 */

public class AlgorithmController {
    private DatabaseConnection databaseConnection;

    /**
     * Constructor for the Algorithmcontroller.
     * @author Ida Nordenswan
     */

    public AlgorithmController() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    /**
     * Updates the gauge when a new drink is registered by the user and updates the database with the max gauge time.
     * @param beverageId the Id of the logged drink.
     * @param beverageAmount the amount of beverage consumed(in centiliters).
     * @author Ida Nordenswan, Louis Brown
     */

    public void updateGaugeNewLog(int beverageId, double beverageAmount) {
        if (lastLog() == null) {
            return;
        }
        double caffeine = getBeverageConcentration(beverageId) * beverageAmount;
        double newDrinkTime = calculateTime(caffeine);
        CustomGauge cg = CustomGauge.getInstance();
        double currentDrinkTime = cg.getCurrentValue();
        newDrinkTime += currentDrinkTime;
        cg.changeValue((int) newDrinkTime);

        String sql = "UPDATE users SET current_max_gauge_time = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newDrinkTime);
            stmt.setInt(2, UserController.getInstance().getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("currentMaxGaugeTime updated successfully for user with ID ");
            } else {
                System.out.println("Failed to update currentMaxGaugeTime for user with ID ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("AlgorithmController : updateGaugeNewLog : Database or SQL error.");
        }
    }

    /**
     * Calculates the current value of the gauge. The current value is based on the last log time and current time.
     * @return the current value of the gauge.
     * @author Ida Nordenswan, Louis Brown
     */

    public double currentGaugeValue() {
        double currentMax = getMaxValue();
        LocalTime lastLogTime = lastLog();
        long hoursSinceLastLog;
        if (lastLogTime == null) {
            hoursSinceLastLog = 0;
        } else {
            LocalTime currentTime = LocalTime.now();
            hoursSinceLastLog = ChronoUnit.HOURS.between(lastLogTime, currentTime);
        }
        return currentMax - hoursSinceLastLog;
    }

    /**
     * Calculates the total amopunt of caffeine that the current user has consumed for the day.
     * @param userId Id of the user.
     * @return total caffein consumption of the day.
     * @author Ida Nordenswan
     *
     */
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
            System.err.println("Error retrieving total caffeine for the day: " + e.getMessage());
            e.printStackTrace();
        }
        return totalCaffeine;
    }

    /**
     * Getter for the max gauge value. Retreives the value from the database for the current user.
     * @return maximum gauge value.
     * @author Ida Nordenswan
     */
    public double getMaxValue() {
        if (lastLog() == null) {
            return 0;
        }
        String sql = "SELECT current_max_gauge_time FROM users WHERE user_id = ?";

        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, UserController.getInstance().getId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("current_max_gauge_time");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving currentMaxGaugeTime: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Gets the corresponding caffeine concentration of the selected beverage.
     * @param beverageId the Id of the beverage.
     * @return the caffeine concentration of the selected beverage.
     * @author Ida Nordenswan
     */
    public double getBeverageConcentration(int beverageId){
        double concentration = 0.0;
        String query= "SELECT caffeine_concentration FROM beverages WHERE beverage_id = ?";

        try (Connection conn = databaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, beverageId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                concentration = resultSet.getDouble("caffeine_concentration");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("AlgorithmController : getBeverageConcentration : SQL or Database error.");
        }
        return concentration;
    }


    /**
     * Checks the last drink log from the database for the current user at the current time.
     * @return the time of the last drink log.
     * @author Ida Nordenswan, Louis Brown
     */
    private LocalTime lastLog() {
        //SQL query to retrieve the time of the last added row for the specified user and today's date
        String sql = "SELECT date AS lastAddedTime FROM userhistory WHERE user_id = ? AND DATE(date) = ? " +
                "ORDER BY date DESC LIMIT 1";
        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, UserController.getInstance().getId());
            stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getTime("lastAddedTime").toLocalTime();
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving last added row time: " + e.getMessage());
            return null;
        }
    }


    /**
     * Half-life equation that calculates the time(in hours) it takes for caffeine to metabolize in the body,
     * using the half-life constant for caffeine = 5.7h.
     * @param c0 is the intitial caffeine contentration in milligrams.
     * @return the time (h) it takes for the caffeine to decrease to 1 milligram.
     * @author Ida Nordenswan
     */
    private double calculateTime(double c0) {
        double cF = 1; // mg
        // hours
        double halfLife = 5.7;
        return -halfLife * Math.log(cF / c0) / Math.log(2);
    }
}
