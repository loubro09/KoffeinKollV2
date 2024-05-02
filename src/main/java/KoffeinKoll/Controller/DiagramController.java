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

public class DiagramController {
    private CircleChart circleDiagram;
    private int userId;

    public DiagramController(CircleChart circleDiagram, int userId) {
        this.circleDiagram = circleDiagram;
        this.userId = userId;
    }

    // Method to update the pie chart with the latest data
    public void updateDiagramData(int days) {
        Map<String, Integer> beverageData = getBeverageConsumptionLastDays(days);
        if (beverageData != null) {
            circleDiagram.updateChartData(beverageData);
        }
    }

    // Fetches beverage consumption data for the last X days from the database
    private Map<String, Integer> getBeverageConsumptionLastDays(int days) {
        Map<String, Integer> beverageData = new LinkedHashMap<>();
        // Dynamically creating the interval part of the SQL query
        String sql = "SELECT b.beverage_name, COUNT(b.beverage_id) AS count " +
                "FROM userhistory uh JOIN beverages b ON uh.beverage_id = b.beverage_id " +
                "WHERE uh.user_id = ? AND uh.date >= current_date - interval '" + days + " days' " +
                "GROUP BY b.beverage_name ORDER BY count DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.userId);
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
    private String getSqlForDays(int days) {
        if (days == 7) {
            return "... WHERE uh.date >= current_date - interval '7 days' ...";
        } else if (days == 30) {
            return "... WHERE uh.date >= current_date - interval '30 days' ...";
        } else {
            throw new IllegalArgumentException("Unsupported time interval: " + days);
        }
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
