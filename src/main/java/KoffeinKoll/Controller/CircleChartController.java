package KoffeinKoll.Controller;

import KoffeinKoll.View.CircleChart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CircleChartController {
    private CircleChart circleDiagram;



    public CircleChartController(CircleChart circleDiagram, int userId) {
        this.circleDiagram = circleDiagram;
    }

    // Method to update the pie chart with the latest data
    public void updateDiagramData(int days) {
        Map<String, Integer> beverageData = getBeverageConsumptionLastDays(days);
        String period = (days == 7) ? "Weekly" : "Monthly"; // Determine the period based on days
        if (beverageData != null) {
            circleDiagram.updateChartData(beverageData, period, days);
        }
    }

    // Fetches beverage consumption data for the last X days from the database
    private Map<String, Integer> getBeverageConsumptionLastDays(int days) {
        Map<String, Integer> beverageData = new LinkedHashMap<>();
        String sql = "SELECT b.beverage_name, COUNT(b.beverage_id) AS count " +
                "FROM userhistory uh JOIN beverages b ON uh.beverage_id = b.beverage_id " +
                "WHERE uh.user_id = ? AND uh.date >= current_date - interval '" + days + " days' " +
                "GROUP BY b.beverage_name ORDER BY count DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, UserController.getInstance().getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    beverageData.put(rs.getString("beverage_name"), rs.getInt("count"));
                }
            }
        } catch (SQLException e) {
            showErrorAlert("Error fetching beverage consumption data", e.getMessage());
        }
        return beverageData;
    }

    // Method to show error alerts
    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
