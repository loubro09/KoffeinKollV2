package KoffeinKoll.View;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * StapelDiagram class represents a bar chart showing weekly caffeine consumption.
 * It extends BarChart and initializes the chart with data.
 */
public class StapelDiagram extends VBox {
    private BarChart<String, Number> barChart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private Label titleLabel;
    private Label totalLabel;

    /**
     * Constructor for StapelDiagram class.
     * Initializes the bar chart with weekdays as categories and caffeine consumption data.
     * @author                                                                                          //AUTHOR
     */
    public StapelDiagram() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        initializeChart();

        titleLabel = new Label("Weekly Caffeine Consumption");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        totalLabel = new Label();
        totalLabel.setStyle("-fx-font-size: 14px;");

        this.getChildren().addAll(titleLabel, barChart, totalLabel);
        this.setSpacing(10);
    }

    private void initializeChart() {
        barChart.setTitle("Caffeine Consumption");
        xAxis.setLabel("Date");
        yAxis.setLabel("Caffeine Amount (mg)");
        barChart.setLegendVisible(false);
    }

    public void updateChartData(Map<String, Number> data, String period, int days) {
        // Clear old chart and reinitialize the axes
        this.getChildren().remove(barChart);
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        initializeChart();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(period + " Caffeine Consumption");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        int totalAmount = data.values().stream().mapToInt(Number::intValue).sum();
        data.forEach((date, amount) -> {
            LocalDate localDate = LocalDate.parse(date);
            String formattedDate = localDate.format(formatter);
            series.getData().add(new XYChart.Data<>(formattedDate, amount));
        });

        barChart.getData().add(series);
        this.getChildren().add(1, barChart); // Add chart back at specific position

        titleLabel.setText(period + " Caffeine Consumption");
        totalLabel.setText("Total Consumed: " + totalAmount + " mg");
    }
}