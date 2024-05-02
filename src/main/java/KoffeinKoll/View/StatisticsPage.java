package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import KoffeinKoll.Controller.DiagramController;

public class StatisticsPage extends A_Page {
    private boolean isCircleChartShown = false;
    private JFXButton btn_goHome;
    private JFXButton btn_weekly;
    private JFXButton btn_monthly;
    private JFXButton btn_info;
    private CircleChart circleChart;
    private DiagramController diagramController;
    private int userId = 1;  // Example user ID, adjust as necessary

    @Override
    public void initializeUI() {
        circleChart = new CircleChart();
        diagramController = new DiagramController(circleChart, userId);
        setComponents();
        setEvents();
        setScene();
        updateChartData(7);  // Load initial data for the last 7 days
    }

    @Override
    public void setComponents() {
        setButtons();
    }

    @Override
    public void setEvents() {
        btn_goHome.setOnAction(event -> goToHomePage());
        btn_weekly.setOnAction(event -> updateChartData(7));  // Update to show last 7 days
        btn_monthly.setOnAction(event -> updateChartData(30));  // Update to show last 30 days
        btn_info.setOnAction(event -> goToInfoPage());
    }

    @Override
    public void setScene() {
        VBox chartAndSwitchBox = new VBox();
        chartAndSwitchBox.setAlignment(Pos.CENTER);
        chartAndSwitchBox.setPadding(new Insets(10));
        chartAndSwitchBox.setSpacing(20);

        StackPane chartPane = new StackPane();
        chartPane.getChildren().add(circleChart);  // Display the circle chart by default
        chartAndSwitchBox.getChildren().add(chartPane);

        borderPane.setCenter(chartAndSwitchBox);

        HBox buttonHBox = new HBox(40);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(btn_goHome, btn_weekly, btn_monthly, btn_info);
        buttonHBox.setPadding(new Insets(20));

        borderPane.setBottom(buttonHBox);
    }

    private void setButtons() {
        btn_goHome = new JFXButton("Home");
        btn_weekly = new JFXButton("Weekly");
        btn_monthly = new JFXButton("Monthly");
        btn_info = new JFXButton("Statistics Info");

        // Apply a consistent style to all buttons
        btn_goHome.setStyle(setButtonStyle());
        btn_weekly.setStyle(setButtonStyle());
        btn_monthly.setStyle(setButtonStyle());
        btn_info.setStyle(setButtonStyle());
    }

    private void updateChartData(int days) {
        diagramController.updateDiagramData(days);  // Delegate data fetching and chart updating to the controller
    }

    private void goToHomePage() {
        changePage(new HomePage());
    }

    private void goToInfoPage() {
        // Navigate to the info page, if any
    }
}
