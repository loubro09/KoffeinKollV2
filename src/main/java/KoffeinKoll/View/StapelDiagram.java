package KoffeinKoll.View;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * StapelDiagram class represents a bar chart showing weekly caffeine consumption.
 * It extends BarChart and initializes the chart with data.
 */
public class StapelDiagram extends BarChart<String, Number> {

    /**
     * Constructor for StapelDiagram class.
     * Initializes the bar chart with weekdays as categories and sample caffeine consumption data.
     * @author                                                                                          //AUTHOR
     */
    public StapelDiagram() {
        super(new CategoryAxis(), new NumberAxis());
        this.setTitle("Weekly Caffeine Consumption");
        this.getXAxis().setLabel("Weekdays");
        this.getYAxis().setLabel("Caffeine Amount");

        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Weekly Caffeine Consumption");

        //Sample caffeine data for each weekday
        double[] caffeineAmount = {100, 150, 120, 180, 200, 90, 80}; // Sample data

        //Add data points for each weekday
        for (int i = 0; i < weekdays.length; i++) {
            series.getData().add(new XYChart.Data<>(weekdays[i], caffeineAmount[i]));

        }

        this.getData().add(series);

    // TO-DO Ändra färgerna i bars?
        // Set the color of the bars for each data point individually
      /*  for (int i = 0; i < series.getData().size(); i++) {
            String barColorStyle = "-fx-bar-fill: rgb(0, 70, 0);";
            series.getData().get(i).getNode().setStyle(barColorStyle);

        }*/
    }
}
