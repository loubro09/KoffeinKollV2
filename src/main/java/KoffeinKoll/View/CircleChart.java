package KoffeinKoll.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

import java.util.Map;

/**
 * CircleChart class represents a custom circular chart component.
 * It extends StackPane and contains a Tile representing a donut chart.
 */
public class CircleChart extends VBox {
    private PieChart pieChart;
    private Label lbl_title;
    private Label lbl_total;

    /**
     * Constructs a new CircleChart instance.
     *
     * @author Alanah Coleman
     */
    public CircleChart() {
        pieChart = new PieChart();
        initializeChart();

        lbl_title = new Label("Beverage Consumption");
        lbl_title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        lbl_total = new Label();
        lbl_total.setStyle("-fx-font-size: 14px;");

        this.getChildren().addAll(lbl_title, pieChart, lbl_total);
        this.setSpacing(10);
    }


    public void updateChartData(Map<String, Integer> data, String period, int days) {
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        int totalAmount = data.values().stream().mapToInt(Integer::intValue).sum();

        data.forEach((name, count) -> {
            double percentage = count * 100.0 / totalAmount;
            PieChart.Data slice = new PieChart.Data(name + " (" + String.format("%.1f%%", percentage) + ")", count);
            chartData.add(slice);
        });

        pieChart.setData(chartData);
        lbl_title.setText(period + " Consumption");
        lbl_total.setText("Total Consumed: " + totalAmount + " units");

        animateChart();
    }


    private void initializeChart() {
        pieChart.setTitle("Beverage Consumption");
        pieChart.setLegendVisible(true);
    }

    private void animateChart() {
        pieChart.getData().forEach(data -> {
            data.getNode().setOnMouseEntered(e -> {
                data.getNode().setStyle("-fx-scale-x: 1.1; -fx-scale-y: 1.1;");
            });
            data.getNode().setOnMouseExited(e -> {
                data.getNode().setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0;");
            });
        });
    }
}