package KoffeinKoll.Controller;

import KoffeinKoll.View.StapelDiagram;
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
        } else {
            showErrorAlert("No Data Available", "No caffeine data available for the selected period.");
        }
    }

    private Map<String, Number> getLastDaysCaffeineConsumption(int userId, int days) {
        Map<String, Number> data = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(days - 1);

        // Pre-populate the map with all dates set to zero
        for (LocalDate date = startDate; !date.isAfter(currentDate); date = date.plusDays(1)) {
            data.put(date.toString(), 0);
        }

        String sql = "SELECT DATE(u.date) as date, SUM(u.amount * b.caffeine_concentration) AS total_caffeine " +
                "FROM userhistory u JOIN beverages b ON u.beverage_id = b.beverage_id " +
                "WHERE u.user_id = ? AND u.date BETWEEN ? AND ? " +
                "GROUP BY DATE(u.date) ORDER BY DATE(u.date)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDate(2, java.sql.Date.valueOf(startDate));
            pstmt.setDate(3, java.sql.Date.valueOf(currentDate));

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    data.put(resultSet.getDate("date").toString(), resultSet.getDouble("total_caffeine"));
                }
            }
        } catch (SQLException e) {
            showErrorAlert("Error fetching caffeine consumption data", e.getMessage());
        }

        return data;
    }

    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
