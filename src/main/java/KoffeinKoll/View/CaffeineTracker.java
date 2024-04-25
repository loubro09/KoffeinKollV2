package KoffeinKoll.View;

import KoffeinKoll.Controller.CaffeineCalculator;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class CaffeineTracker extends Application {

    private static CaffeineCalculator caffeineCalculator;


    // Sample data
    private static final double RECOMMENDED_CAFFEINE = 400; // mg
    private static final double CONSUMED_CAFFEINE = 200; // mg

    @Override
    public void start(Stage stage) {
        // Calculate the percentage consumed
        int percentageConsumed = (int) ((CONSUMED_CAFFEINE / RECOMMENDED_CAFFEINE) * 100);

        // Create a bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Caffeine Intake");
        xAxis.setLabel("Category");
        yAxis.setLabel("Caffeine (mg)");
        barChart.setBackground(Background.fill(Color.TRANSPARENT));


        // Create series for recommended and consumed caffeine
        XYChart.Series<String, Number> recommendedSeries = new XYChart.Series<>();
        recommendedSeries.setName("Recommended");
        recommendedSeries.getData().add(new XYChart.Data<>("Caffeine", RECOMMENDED_CAFFEINE));

        XYChart.Series<String, Number> consumedSeries = new XYChart.Series<>();
        consumedSeries.setName("Consumed");
        consumedSeries.getData().add(new XYChart.Data<>("Caffeine", CONSUMED_CAFFEINE));

        // Add series to the bar chart
        barChart.getData().addAll(recommendedSeries, consumedSeries);

        // Create a VBox to hold the bar chart and the text
        VBox vbox = new VBox();
        vbox.getChildren().addAll(barChart, createPercentageText(percentageConsumed));
        vbox.setAlignment(Pos.CENTER);

        // Display the scene
        Scene scene = new Scene(vbox, 400, 300);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Caffeine Tracker");
        stage.show();
    }

    // Method to create the text node displaying the percentage consumed
    private Text createPercentageText(double percentage) {
        Text text = new Text("Consumed: " + percentage + "% of recommended amount");
        return text;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
