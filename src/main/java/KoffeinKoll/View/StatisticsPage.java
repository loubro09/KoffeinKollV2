package KoffeinKoll.View;

import KoffeinKoll.Controller.CircleChartController;
import KoffeinKoll.Controller.StapelDiagramController;
import KoffeinKoll.Controller.UserController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * StatisticsPage class represents the statistics page of the application.
 * It displays the chart and provides buttons for navigation.
 */
public class StatisticsPage extends A_Page {
    private boolean isCircleChartShown = true;
    private JFXButton btn_goHome;
    private JFXButton btn_toggleChart;
    private JFXButton btn_weekly;
    private JFXButton btn_monthly;
    private JFXButton btn_info;
    private CircleChart circleChart;
    private StapelDiagram stapelDiagram;
    private CircleChartController circleChartController;
    private StapelDiagramController stapelDiagramController;
    private UserController userController;
    private StackPane chartPane;

    /**
     * Initializes the user interface components of the StatisticsPage.
     *
     * @author Louis Brown
     */
    @Override
    public void initializeUI() {
        userController = UserController.getInstance();
        circleChart = new CircleChart();
        stapelDiagram = new StapelDiagram();
        circleChartController = new CircleChartController(circleChart);
        stapelDiagramController = new StapelDiagramController(stapelDiagram);

        setComponents();
        setEvents();
        setScene();
        updateChartData(7);
    }

    /**
     * Sets up the UI components of the StatisticsPage.
     *
     * @author Louis Brown
     */
    @Override
    public void setComponents() {
        btn_goHome = new JFXButton("Home");
        btn_toggleChart = new JFXButton("View caffeine consumption statistics");
        btn_weekly = new JFXButton("Weekly");
        btn_monthly = new JFXButton("Monthly");
        btn_info = new JFXButton("Info");

        chartPane = new StackPane();
        chartPane.getChildren().add(circleChart);

        setButtons();
    }

    /**
     * Sets up the event handlers for the buttons.
     *
     * @author Alanah Coleman
     */
    @Override
    public void setEvents() {
        btn_goHome.setOnAction(event -> goToHomePage());
        btn_toggleChart.setOnAction(event -> toggleChart());
        btn_weekly.setOnAction(event -> updateChartData(7));
        btn_monthly.setOnAction(event -> updateChartData(30));
        btn_info.setOnAction(event -> goToInfoPage());
    }

    /**
     * Sets up the scene of the StatisticsPage.
     *
     * @author Alanah Coleman
     */
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

    /**
     * Sets up the buttons for navigation.
     *
     * @author Alanah Coleman
     */
    private void setButtons() {
        btn_goHome.setStyle(setButtonStyle());
        btn_toggleChart.setStyle(setButtonStyle());
        btn_weekly.setStyle(setButtonStyle());
        btn_monthly.setStyle(setButtonStyle());
        btn_info.setStyle(setButtonStyle());
    }

    /**
     * Toggles between displaying the circle chart and the bar chart.
     * @author Elias Olsson
     */
    private void toggleChart() {
        isCircleChartShown = !isCircleChartShown;
        chartPane.getChildren().clear();
        if (isCircleChartShown) {
            btn_toggleChart.setText("View caffeine consumption statistics");
            chartPane.getChildren().add(circleChart);
            updateChartData(7);
        } else {
            btn_toggleChart.setText("View beverage consumption statistics");
            chartPane.getChildren().add(stapelDiagram);
            updateChartData(7);
        }
    }

    /**
     * Updates the chart data based on the selected time period.
     * @param days The number of days for which the data is displayed.
     * @author Alanah Coleman
     */
    private void updateChartData(int days) {
        if (days == 7) {
            btn_weekly.setDisable(true);
            btn_monthly.setDisable(false);
        }
        else {
            btn_weekly.setDisable(false);
            btn_monthly.setDisable(true);
        }
        if (isCircleChartShown) {
            circleChartController.updateDiagramData(days);
        } else {
            stapelDiagramController.updateDiagramData(userController.getId(), days);
        }
    }

    /**
     * Navigates to the home page.
     *
     * @author Louis Brown
     */
    private void goToHomePage() {
        changePage(new HomePage());
    }

    /**
     * Navigates to the info page.
     *
     * @author Louis Brown
     */
    private void goToInfoPage() {
        changePage(new InfoPage(false));
    }
}