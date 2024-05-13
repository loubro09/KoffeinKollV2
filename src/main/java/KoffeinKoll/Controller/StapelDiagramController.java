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

/**
 * Controller class for managing data for the StapelDiagram view.
 */
public class StapelDiagramController {
    private StapelDiagram stapelDiagram;

    /**
     * Constructor for StapelDiagramController class.
     * @param stapelDiagram The associated StapelDiagram view.
     * @author Kenan Al Tal
     */
    public StapelDiagramController(StapelDiagram stapelDiagram) {
        this.stapelDiagram = stapelDiagram;
    }

    /**
     * Updates the chart data for the specified user and time period.
     * @param userId The ID of the user.
     * @param days The number of days for the time period.
     * @author Kenan Al Tal
     */
    public void updateDiagramData(int userId, int days) {
        Map<String, Number> data = getLastDaysCaffeineConsumption(userId, days);
        String period = (days == 7) ? "Weekly" : (days == 30) ? "Monthly" : "Custom";
        if (data != null && !data.isEmpty()) {
            stapelDiagram.updateChartData(data, period, days);
        } else {
            showErrorAlert("No Data Available", "No caffeine data available for the selected period.");
        }
    }

    /**
     * Retrieves the caffeine consumption data for the specified user and time period.
     * @param userId The ID of the user.
     * @param days The number of days for the time period.
     * @return A map containing the caffeine consumption data for each date.
     * @author Kenan Al Tal,
     */

    private Map<String, Number> getLastDaysCaffeineConsumption(int userId, int days) {
        Map<String, Number> data = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(days - 1);

        for (LocalDate date = startDate; !date.isAfter(currentDate); date = date.plusDays(1)) {
            data.put(date.toString(), 0);
            System.out.println(date.toString());
        }

        String sql = "SELECT DATE(u.date) as date, SUM(u.amount * b.caffeine_concentration) AS total_caffeine\n" +
                "FROM userhistory u\n" +
                "JOIN beverages b ON u.beverage_id = b.beverage_id\n" +
                "WHERE u.user_id = ? AND u.date >= ? AND u.date < ?\n" +
                "GROUP BY DATE(u.date)\n" +
                "ORDER BY DATE(u.date)\n";


        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDate(2, java.sql.Date.valueOf(startDate));
            pstmt.setDate(3, java.sql.Date.valueOf(currentDate.plusDays(1)));

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    data.put(resultSet.getDate("date").toString(), resultSet.getDouble("total_caffeine"));
                }
            }
        } catch (SQLException e) {
            showErrorAlert("Error fetching caffeine consumption data", e.getMessage());
            System.out.println("StapelDiagramController, getLastDaysCaffeineConsumption: ");
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Displays an error alert with the specified header and content.
     * @param header The header text for the alert.
     * @param content The content text for the alert.
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
