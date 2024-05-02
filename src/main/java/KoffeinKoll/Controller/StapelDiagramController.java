package KoffeinKoll.Controller;

import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class StapelDiagramController {

    public XYChart.Series<String, Number> getLast30DaysCaffeineConsumption(int userId) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Last 30 Days Caffeine Consumption");

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(29); // Start date is 30 days ago

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            for (int i = 0; i < 30; i++) {
                LocalDate date = startDate.plusDays(i);
                double caffeineAmount = getCaffeineAmountForDay(connection, date, userId);
                series.getData().add(new XYChart.Data<>(date.toString(), caffeineAmount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }

        return series;
    }

    public XYChart.Series<String, Number> getLast7DaysCaffeineConsumption(int userId) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Last 7 Days Caffeine Consumption");

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(6); // Start date is 7 days ago

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            for (int i = 0; i < 7; i++) {
                LocalDate date = startDate.plusDays(i);
                double caffeineAmount = getCaffeineAmountForDay(connection, date, userId);
                series.getData().add(new XYChart.Data<>(getDayOfWeekName(date.getDayOfWeek()), caffeineAmount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }

        return series;
    }

    private double getCaffeineAmountForDay(Connection connection, LocalDate date, int userId) throws SQLException {
        double totalCaffeine = 0;

        String sql = "SELECT u.amount, b.caffeine_concentration " +
                "FROM userhistory u " +
                "JOIN beverages b ON u.beverage_id = b.beverage_id " +
                "WHERE u.date = ? AND u.user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(date));
            pstmt.setInt(2, userId);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    double amount = resultSet.getDouble("amount");
                    double caffeineConcentration = resultSet.getDouble("caffeine_concentration");

                    // Calculate caffeine consumed for this beverage
                    double caffeineConsumed = amount * caffeineConcentration;
                    totalCaffeine += caffeineConsumed;
                }
            }
        }
        return totalCaffeine;
    }

    private String getDayOfWeekName(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "Monday";
            case TUESDAY:
                return "Tuesday";
            case WEDNESDAY:
                return "Wednesday";
            case THURSDAY:
                return "Thursday";
            case FRIDAY:
                return "Friday";
            case SATURDAY:
                return "Saturday";
            case SUNDAY:
                return "Sunday";
            default:
                return "";
        }
    }
}