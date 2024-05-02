package KoffeinKoll.View;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class StapelDiagram extends BarChart<String, Number> {

    public StapelDiagram(XYChart.Series<String, Number> data) {
        super(new CategoryAxis(), new NumberAxis());
        this.setTitle("Weekly Caffeine Consumption");
        this.getXAxis().setLabel("Weekdays");
        this.getYAxis().setLabel("Caffeine Amount");

        if (data != null) {
            this.getData().add(data);
        }
    }
}