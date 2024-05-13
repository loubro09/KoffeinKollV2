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
    private Label lbl_title;
    private Label lbl_total;

    /**
     * Constructor for StapelDiagram class.
     * Initializes the bar chart with weekdays as categories and caffeine consumption data.
     * @author Alanah Coleman
     */
    public StapelDiagram() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        initializeChart();

        lbl_title = new Label("Weekly Caffeine Consumption");
        lbl_title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        lbl_total = new Label();
        lbl_total.setStyle("-fx-font-size: 14px;");

        this.getChildren().addAll(lbl_title, barChart, lbl_total);
        this.setSpacing(10);
    }


    /**
     * When new drinks are logged by the user The updateChartData updates the bar chart with new data.
     *
     * @param data   the chart is representing the caffeine consumption by the user and is categorized by date.
     * @param period Selected period for wich the data is displayed.
     * @param days   number of days in the period
     * @author Ida Nordenswan, Kenan Al Tal
     */

    public void updateChartData(Map<String, Number> data, String period, int days) {
        this.getChildren().remove(barChart);
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        initializeChart();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(period + " Caffeine Consumption");
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        int totalAmount = data.values().stream().mapToInt(Number::intValue).sum();
        data.forEach((date, amount) -> {
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            String formattedDate = localDate.format(inputFormatter);
            series.getData().add(new XYChart.Data<>(formattedDate, amount));
        });

        barChart.getData().add(series);
        this.getChildren().add(1, barChart);

        lbl_title.setText(period + " Caffeine Consumption");
        lbl_total.setText("Total Consumed: " + totalAmount + " mg");
    }

    /**
     * Initializes the chart. Titles and labels are set.
     *
     * @author Ida Nordenswan
     */

    private void initializeChart() {
        barChart.setTitle("Caffeine Consumption");
        xAxis.setLabel("Date");
        yAxis.setLabel("Caffeine Amount (mg)");
        barChart.setLegendVisible(false);
    }
}