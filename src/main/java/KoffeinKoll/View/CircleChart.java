package KoffeinKoll.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import java.util.Map;

public class CircleChart extends PieChart {

    public CircleChart() {
        super();
        initializeChart();
    }

    private void initializeChart() {
        this.setTitle("Beverage Consumption");
        // Set more properties as needed, such as chart labels
        this.setLegendVisible(true);  // Optionally set the legend visibility
    }

    // Update chart data from a map where keys are beverage names and values are their counts
    public void updateChartData(Map<String, Integer> data) {
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

        data.forEach((beverageName, count) -> {
            chartData.add(new PieChart.Data(beverageName, count));
        });

        this.setData(chartData);
        animateChart();
    }

    // Optional: animate the chart updates (can be customized further as needed)
    private void animateChart() {
        this.getData().forEach(data -> {
            data.getNode().setOnMouseEntered(e -> {
                data.getNode().setStyle("-fx-scale-x: 1.1; -fx-scale-y: 1.1;");
            });
            data.getNode().setOnMouseExited(e -> {
                data.getNode().setStyle("-fx-scale-x: 1.0; -fx-scale-y: 1.0;");
            });
        });
    }
}
