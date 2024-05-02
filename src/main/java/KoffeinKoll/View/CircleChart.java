package KoffeinKoll.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.Map;

public class CircleChart extends VBox {
    private PieChart pieChart;
    private Label titleLabel;
    private Label totalLabel;

    public CircleChart() {
        pieChart = new PieChart();
        initializeChart();

        titleLabel = new Label("Beverage Consumption");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        totalLabel = new Label();
        totalLabel.setStyle("-fx-font-size: 14px;");

        this.getChildren().addAll(titleLabel, pieChart, totalLabel);
        this.setSpacing(10);
    }

    private void initializeChart() {
        pieChart.setTitle("Beverage Consumption");
        pieChart.setLegendVisible(true);
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
        titleLabel.setText(period + " Consumption");
        totalLabel.setText("Total Consumed: " + totalAmount + " units");

        animateChart();
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
