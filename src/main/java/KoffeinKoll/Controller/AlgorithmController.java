package KoffeinKoll.Controller;

import KoffeinKoll.View.CustomGauge;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * The AlgorithmController class handles methods for calculating caffeine metabolism time. It also updates the gauge when a new consumed beverage is logged, and gets information needed from the database.
 * @author idanordenswan
 */

public class AlgorithmController {
    private DatabaseConnection databaseConnection;

    /**
     * Constructor for the Algorithmcontroller.
     *
     */

    public AlgorithmController() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    /**
     * Half-life equation that calculates the time(in hours) it takes for caffeine to metabolize in the body, using the half-life constant for caffeine = 5.7h.
     * @param c0 is the intitial caffeine contentration in milligrams.
     * @return the time (h) it takes for the caffeine to decrease to 1 milligram.
     * @author idanordenswan
     */

    public double calculateTime(double c0) {
        double cF = 1; // mg
        // hours
        double halfLife = 5.7;
        System.out.println("calculateTime: Halflife: " + halfLife);
        return -halfLife * Math.log(cF / c0) / Math.log(2);
    }

    /**
     * Upda<tes the gauge when a new drink is registered by the user and updates the database with the max gauge time.
     * @param beverageId the Id of the logged drink.
     * @param beverageAmount the amount of beverage consumed(in centiliters).
     * @author idanordenswan
     */

    public void updateGaugeNewLog(int beverageId, double beverageAmount) {
        double caffeine = getBeverageConcentration(beverageId) * beverageAmount;
        System.out.println("updateGaugeNewLog: Caffeine amount: " + caffeine);
        double newDrinkTime = calculateTime(caffeine);
        System.out.println("updateGaugeNewLog: Time for new drink to leave body: " + newDrinkTime);
        CustomGauge cg = CustomGauge.getInstance();
        double currentDrinkTime = cg.getCurrentValue();
        System.out.println("Time for current drink to leave body: " + currentDrinkTime);
        newDrinkTime += currentDrinkTime;
        System.out.println("New time for caffeine to leave body: " + newDrinkTime);
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

    /**
     * Calculates the current value of the gauge. The current value is based on the last log time and current time.
     * @return the current value of the gauge.
     * @author idanordenswan
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
     * Getter for the max gauge value. Retreives the value from the database for the current user.
     * @return maximum gauge value.
     * @author idanordenswan
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

    /**
     * Checks the last drink log from the database for the current user at the current time.
     * @return the time of the last drink log.
     * @author idanordenswan
     */

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

    /**
     * Gets the corresponding caffeine concentration of the selected beverage.
     * @param beverageId the Id of the beverage.
     * @return the caffeine concentration of the selected beverage.
     * @author idanordenswan
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
                System.out.println("getBeverageConcentration: Caffeine concentration = " + concentration);
            }else{
                System.out.println("beverage cannot be found in database");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return concentration;
    }

    /**
     * Calculates the total amopunt of caffeine that the current user has consumed for the day.
     * @param userId Id of the user.
     * @return total caffein consumption of the day.
     * @author idanordenswan
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
        return calculateTime(totalCaffeine);
    }
}
