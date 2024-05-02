package KoffeinKoll.View;

import KoffeinKoll.Controller.StapelDiagramController;
import com.jfoenix.controls.JFXButton;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.chart.XYChart;

public class StatisticsPage extends A_Page{
    private boolean isCircleChartShown = false; // Flag to track the currently shown chart
    private JFXButton btn_goHome;
    private JFXButton btn_weekly;
    private JFXButton btn_monthly;
    private JFXButton btn_info;
    private StapelDiagramController stapelDiagramController;

    @Override
    public void initializeUI() {
        setComponents();
        setEvents();
        setScene();
    }

    @Override
    public void setComponents() {
        setButtons();
    }

    @Override
    public void setEvents() {
        btn_goHome.setOnAction(event -> goToHomePage());
        btn_weekly.setOnAction(event -> showWeeklyStatistics());
        btn_monthly.setOnAction(event -> showMonthlyStatistics());
        btn_info.setOnAction(event -> showInfoPage());
    }

    @Override
    public void setScene() {
        // Create a VBox for the switch tile and chart
        VBox chartAndSwitchBox = new VBox();
        chartAndSwitchBox.setAlignment(Pos.CENTER);
        chartAndSwitchBox.setPadding(new Insets(10));
        chartAndSwitchBox.setSpacing(20);

        // Create a Pane for chart display
        StackPane chartPane = new StackPane();
        StapelDiagram stapelDiagram = new StapelDiagram(null); // Initially no data
        stapelDiagram.setMaxSize(400, 400);
        chartPane.getChildren().add(stapelDiagram);
        chartAndSwitchBox.getChildren().add(chartPane);

        // Create a VBox for the switch tile
        Tile switchTile = createSwitchTile(chartPane);
        VBox.setMargin(switchTile, new Insets(10, 0, 0, 0)); // Add some margin
        chartAndSwitchBox.getChildren().add(switchTile);

        // Set the chart and switch box at the center of the BorderPane
        borderPane.setCenter(chartAndSwitchBox);

        // Create a VBox for buttons
        HBox buttonHBox = new HBox(40);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(btn_goHome, btn_weekly, btn_monthly, btn_info);
        buttonHBox.setPadding(new Insets(20));

        // Set the button box at the bottom of the BorderPane
        borderPane.setBottom(buttonHBox);
    }

    private void setButtons() {
        btn_goHome = new JFXButton("Home");
        btn_weekly = new JFXButton("Weekly");
        btn_monthly = new JFXButton("Monthly");
        btn_info = new JFXButton("Statistics Info");

        btn_goHome.setStyle(setButtonStyle());
        btn_weekly.setStyle(setButtonStyle());
        btn_monthly.setStyle(setButtonStyle());
        btn_info.setStyle(setButtonStyle());
    }

    // Method to create the switch tile
    private Tile createSwitchTile(StackPane chartPane) {
        Tile switchTile = TileBuilder.create()
                .skinType(Tile.SkinType.SWITCH)
                .title("Switch Chart")
                .text("Switch between Circle and Stapel Diagrams")
                .build();
        switchTile.setStyle("-fx-background-color: white;");
        switchTile.setBackgroundColor(Color.WHITE);
        switchTile.setMaxSize(50, 50);
        switchTile.setOnSwitchPressed(event -> toggleChart(chartPane));
        return switchTile;
    }

    // Method to toggle between circle and stapel diagrams
    private void toggleChart(StackPane chartPane) {
        if (isCircleChartShown) {
            // Remove the circle chart and add the stapel diagram
            chartPane.getChildren().clear();
            StapelDiagram stapelDiagram = new StapelDiagram(null); // Initially no data
            stapelDiagram.setMaxSize(400, 400);
            chartPane.getChildren().add(stapelDiagram);
        } else {
            // Remove the stapel diagram and add the circle chart
            chartPane.getChildren().clear();
            CircleChart circleChart = new CircleChart();
            circleChart.setMaxSize(400, 400);
            chartPane.getChildren().add(circleChart);
        }
        isCircleChartShown = !isCircleChartShown; // Toggle the flag
    }

    // Method to navigate to the home page
    private void goToHomePage() {
        changePage(new HomePage());
    }

    // Method to navigate to the weekly statistics page
    private void showWeeklyStatistics() {
        // Fetch weekly caffeine consumption data from the controller
        XYChart.Series<String, Number> weeklyData = stapelDiagramController.getLast7DaysCaffeineConsumption(1); // Pass the user ID
        // Create a new instance of StapelDiagram and set the weekly statistics
        StapelDiagram stapelDiagram = new StapelDiagram(weeklyData);
        // Update the chart displayed
        updateChart(stapelDiagram);
    }

    private void showMonthlyStatistics() {
        // Fetch monthly caffeine consumption data from the controller
        XYChart.Series<String, Number> monthlyData = stapelDiagramController.getLast30DaysCaffeineConsumption(1); // Pass the user ID
        // Create a new instance of StapelDiagram and set the monthly statistics
        StapelDiagram stapelDiagram = new StapelDiagram(monthlyData);
        // Update the chart displayed
        updateChart(stapelDiagram);
    }

    // Method to navigate to the info page
    private void showInfoPage() {
        changePage(new DiagramInfoPage());
    }

    private void updateChart(StapelDiagram stapelDiagram) {
        // Remove existing chart
        VBox chartAndSwitchBox = (VBox) borderPane.getCenter();
        StackPane chartPane = (StackPane) chartAndSwitchBox.getChildren().get(0);
        chartPane.getChildren().clear();
        // Add the new chart
        chartPane.getChildren().add(stapelDiagram);
    }
}