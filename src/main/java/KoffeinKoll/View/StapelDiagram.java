package KoffeinKoll.View;

import KoffeinKoll.Controller.StatisticsController;
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

    // TO-DO Ändra färgerna i bars?
        // Set the color of the bars for each data point individually
      /*  for (int i = 0; i < series.getData().size(); i++) {
            String barColorStyle = "-fx-bar-fill: rgb(0, 70, 0);";
            series.getData().get(i).getNode().setStyle(barColorStyle);

        }*/
    }