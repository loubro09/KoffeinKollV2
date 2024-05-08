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

/**
 * Controller class for managing the data and actions related to a CircleChart.
 * @author Elias Olsson
 */
public class CircleChartController {
    private CircleChart circleDiagram;

    /**
     * Constructor for CircleChartController.
     * @param circleDiagram The CircleChart instance to be controlled.
     */
    public CircleChartController(CircleChart circleDiagram) {
        this.circleDiagram = circleDiagram;
    }

    /**
     * Method to update the pie chart with the latest data.
     * @param days The number of days for which to retrieve beverage consumption data.
     */
    public void updateDiagramData(int days) {
        Map<String, Integer> beverageData = getBeverageConsumptionLastDays(days);
        String period = (days == 7) ? "Weekly" : "Monthly";
        if (beverageData != null) {
            circleDiagram.updateChartData(beverageData, period, days);
        }
    }

    /**
     * Fetches beverage consumption data for the last X days from the database.
     * @param days The number of days for which to retrieve beverage consumption data.
     * @return A map containing beverage names as keys and their corresponding consumption counts as values.
     */
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

    /**
     * Method to show error alerts.
     * @param header The header text for the error alert.
     * @param content The content text for the error alert.
     * @author Louis Brown
     */
    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
