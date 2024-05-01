package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * StatisticsPage class represents the statistics page of the application.
 * It displays the chart and provides buttons for navigation.
 */
public class StatisticsPage extends A_Page{
    private boolean isCircleChartShown = false; // Flag to track the currently shown chart
    private JFXButton btn_goHome;
    private JFXButton btn_weekly;
    private JFXButton btn_monthly;
    private JFXButton btn_info;

    /**
     * Initializes the user interface components of the StatisticsPage.
     * @author Louis Brown
     */
    @Override
    public void initializeUI() {
        setComponents();
        setEvents();
        setScene();
    }

    /**
     * Sets up the UI components of the StatisticsPage.
     * @author Louis Brown
     */
    @Override
    public void setComponents() {
        setButtons();
    }

    /**
     * Sets up the event handlers for the buttons.
     * @author                                                                                          //AUTHOR
     */
    @Override
    public void setEvents() {
        btn_goHome.setOnAction(event -> goToHomePage());
        btn_weekly.setOnAction(event -> goToWeeklyStatisticsPage());
        btn_monthly.setOnAction(event -> goToMonthlyStatisticsPage());
        btn_info.setOnAction(event -> goToInfoPage());
    }

    /**
     * Sets up the scene of the StatisticsPage.
     * @author                                                                                          //AUTHOR
     */
    @Override
    public void setScene() {
        VBox chartAndSwitchBox = new VBox();
        chartAndSwitchBox.setAlignment(Pos.CENTER);
        chartAndSwitchBox.setPadding(new Insets(10));
        chartAndSwitchBox.setSpacing(20);

        StackPane chartPane = new StackPane();
        StapelDiagram stapelDiagram = new StapelDiagram(); // Show the stapel diagram initially
        stapelDiagram.setMaxSize(400, 400);
        chartPane.getChildren().add(stapelDiagram);
        chartAndSwitchBox.getChildren().add(chartPane);

        Tile switchTile = createSwitchTile(chartPane);
        VBox.setMargin(switchTile, new Insets(10, 0, 0, 0)); // Add some margin
        chartAndSwitchBox.getChildren().add(switchTile);

        borderPane.setCenter(chartAndSwitchBox);

        HBox buttonHBox = new HBox(40);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(btn_goHome, btn_weekly, btn_monthly, btn_info);
        buttonHBox.setPadding(new Insets(20));

        borderPane.setBottom(buttonHBox);
    }

    /**
     * Sets up the buttons for navigation.
     * @author                                                                                          //AUTHOR
     */
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

    /**
     * Creates the switch tile for toggling between chart types.
     * @param chartPane The pane containing the chart to toggle.
     * @return The switch tile.
     * @author                                                                                          //AUTHOR
     */
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

    /**
     * Toggles between the circle and stapel diagrams.
     * @param chartPane The pane containing the chart to toggle.
     * @author                                                                                          //AUTHOR
     */
    private void toggleChart(StackPane chartPane) {
        if (isCircleChartShown) {
            //Remove the circle chart and add the stapel diagram
            chartPane.getChildren().clear();
            StapelDiagram stapelDiagram = new StapelDiagram();
            stapelDiagram.setMaxSize(400, 400);
            chartPane.getChildren().add(stapelDiagram);
        } else {
            //Remove the stapel diagram and add the circle chart
            chartPane.getChildren().clear();
            CircleChart circleChart = new CircleChart();
            circleChart.setMaxSize(400, 400);
            chartPane.getChildren().add(circleChart);
        }
        isCircleChartShown = !isCircleChartShown;
    }

    /**
     * Navigates to the home page.
     * @author                                                                                          //AUTHOR
     */
    private void goToHomePage() {
        changePage(new HomePage());
    }

    /**
     * Navigates to the weekly statistics page.
     * @author                                                                                          //AUTHOR
     */
    private void goToWeeklyStatisticsPage() {
        //add code
    }

    /**
     * Navigates to the monthly statistics page.
     * @author                                                                                          //AUTHOR
     */
    private void goToMonthlyStatisticsPage() {
        // Code to navigate to the monthly statistics page goes here
    }

    /**
     * Navigates to the info page.
     * @author                                                                                          //AUTHOR
     */
    private void goToInfoPage() {
        changePage(new DiagramInfoPage());
    }
}
