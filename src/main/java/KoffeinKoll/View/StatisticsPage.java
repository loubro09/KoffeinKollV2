package KoffeinKoll.View;

import KoffeinKoll.Controller.CircleChartController;
import KoffeinKoll.Controller.StapelDiagramController;
import KoffeinKoll.Controller.UserController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StatisticsPage extends A_Page {
    private boolean isCircleChartShown = true;
    private JFXButton btn_goHome, btn_toggleChart, btn_weekly, btn_monthly, btn_info;
    private CircleChart circleChart;
    private StapelDiagram stapelDiagram;
    private CircleChartController circleChartController;
    private StapelDiagramController stapelDiagramController;
    private UserController userController;
    private StackPane chartPane;

    @Override
    public void initializeUI() {
        userController = UserController.getInstance();
        circleChart = new CircleChart();
        stapelDiagram = new StapelDiagram();
        circleChartController = new CircleChartController(circleChart, userController.getId());
        stapelDiagramController = new StapelDiagramController(stapelDiagram);

        setComponents();
        setEvents();
        setScene();
        updateChartData(7);  // Load initial data for the last 7 days
    }

    @Override
    public void setComponents() {
        btn_goHome = new JFXButton("Home");
        btn_toggleChart = new JFXButton("Toggle Chart");
        btn_weekly = new JFXButton("Weekly");
        btn_monthly = new JFXButton("Monthly");
        btn_info = new JFXButton("Info");

        chartPane = new StackPane();
        chartPane.getChildren().add(circleChart); // Display the CircleChart by default

        setButtons();
    }

    private void setButtons() {
        btn_goHome.setStyle(setButtonStyle());
        btn_toggleChart.setStyle(setButtonStyle());
        btn_weekly.setStyle(setButtonStyle());
        btn_monthly.setStyle(setButtonStyle());
        btn_info.setStyle(setButtonStyle());
    }

    @Override
    public void setEvents() {
        btn_goHome.setOnAction(event -> goToHomePage());
        btn_toggleChart.setOnAction(event -> toggleChart());
        btn_weekly.setOnAction(event -> updateChartData(7));
        btn_monthly.setOnAction(event -> updateChartData(30));
        btn_info.setOnAction(event -> goToInfoPage());
    }

    @Override
    public void setScene() {
        VBox chartAndSwitchBox = new VBox();
        chartAndSwitchBox.setAlignment(Pos.CENTER);
        chartAndSwitchBox.setPadding(new Insets(10));
        chartAndSwitchBox.setSpacing(20);
        chartAndSwitchBox.getChildren().addAll(btn_toggleChart, chartPane);

        borderPane.setCenter(chartAndSwitchBox);

        HBox buttonHBox = new HBox(40);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(btn_goHome, btn_weekly, btn_monthly, btn_info);
        buttonHBox.setPadding(new Insets(20));

        borderPane.setBottom(buttonHBox);
    }

    private void toggleChart() {
        isCircleChartShown = !isCircleChartShown;
        chartPane.getChildren().clear();
        if (isCircleChartShown) {
            chartPane.getChildren().add(circleChart);
            updateChartData(7);  // Load data for the last 7 days for the currently displayed chart
        } else {
            chartPane.getChildren().add(stapelDiagram);
            updateChartData(7);  // Assuming similar need for StapelDiagram
        }
    }

    private void updateChartData(int days) {
        if (isCircleChartShown) {
            circleChartController.updateDiagramData(days);
        } else {
            stapelDiagramController.updateDiagramData(userController.getId(), days);
        }
    }

    private void goToHomePage() {
        changePage(new HomePage());
    }

    private void goToInfoPage() {
        changePage(new InfoPage(false));
    }
}
