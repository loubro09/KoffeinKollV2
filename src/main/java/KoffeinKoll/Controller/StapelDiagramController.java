package KoffeinKoll.Controller;

import KoffeinKoll.View.StapelDiagram;
import javafx.scene.chart.XYChart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class StapelDiagramController {
    private StapelDiagram stapelDiagram;

    public StapelDiagramController(StapelDiagram stapelDiagram) {
        this.stapelDiagram = stapelDiagram;
    }

    public void updateDiagramData(int userId, int days) {
        Map<String, Number> data = getLastDaysCaffeineConsumption(userId, days);
        String period = (days == 7) ? "Weekly" : (days == 30) ? "Monthly" : "Custom";
        if (data != null && !data.isEmpty()) {
            stapelDiagram.updateChartData(data, period, days);
        }
    }

    private Map<String, Number> getLastDaysCaffeineConsumption(int userId, int days) {
        Map<String, Number> data = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(days - 1);

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            for (int i = 0; i < days; i++) {
                LocalDate date = startDate.plusDays(i);
                double caffeineAmount = getCaffeineAmountForDay(connection, date, userId);
                data.put(date.toString(), caffeineAmount);
            }
        } catch (SQLException e) {
            showErrorAlert("Error fetching caffeine consumption data", e.getMessage());
        }

        return data;
    }

    private double getCaffeineAmountForDay(Connection connection, LocalDate date, int userId) throws SQLException {
        double totalCaffeine = 0;
        String sql = "SELECT SUM(u.amount * b.caffeine_concentration) AS total_caffeine " +
                "FROM userhistory u JOIN beverages b ON u.beverage_id = b.beverage_id " +
                "WHERE u.date = ? AND u.user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(date));
            pstmt.setInt(2, userId);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    totalCaffeine = resultSet.getDouble("total_caffeine");
                }
            }
        }
        return totalCaffeine;
    }

    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
