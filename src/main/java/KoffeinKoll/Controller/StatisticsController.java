package KoffeinKoll.Controller;

import KoffeinKoll.View.StapelDiagram;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class StatisticsController {

    public XYChart.Series<String, Number> getWeeklyCaffeineConsumption(int userId) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Weekly Caffeine Consumption");

        // Define weekdays
        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        // Fetch caffeine consumption data from the database for the current week
        LocalDate currentDate = LocalDate.now();
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            for (int i = 0; i < weekdays.length; i++) {
                LocalDate date = currentDate.with(DayOfWeek.of(i + 1));
                double caffeineAmount = getCaffeineAmountForDay(connection, date, userId);
                series.getData().add(new XYChart.Data<>(weekdays[i], caffeineAmount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }

        return series;
    }
    public XYChart.Series<String, Number> getMonthlyCaffeineConsumption(int userId) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Caffeine Consumption");

        // Define weekdays
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            for (int day = 1; day <= currentDate.lengthOfMonth(); day++) {
                LocalDate date = LocalDate.of(currentYear, currentMonth, day);
                double caffeineAmount = getCaffeineAmountForDay(connection, date, userId);
                series.getData().add(new XYChart.Data<>(String.valueOf(day), caffeineAmount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }

        return series;
    }


    private double getCaffeineAmountForDay(Connection connection, LocalDate date, int userId) throws SQLException {
        String sql = "SELECT SUM(b.caffeine_amount) AS totalCaffeine " +
                "FROM userhistory u " +
                "JOIN beverages b ON u.beverage_id = b.beverage_id " +
                "WHERE u.date = ? AND u.user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(date));
            pstmt.setInt(2, userId); // Set the user ID parameter
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("totalCaffeine");
                }
            }
        }
        return 0; // Default to 0 if no data found
    }
}