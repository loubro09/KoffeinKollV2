package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StatisticsPage extends Application {

    private Stage statisticsStage;
    private boolean isCircleChartShown = false; // Flag to track the currently shown chart

    @Override
    public void start(Stage stage) throws Exception {
        this.statisticsStage = stage;
        statisticsStage.setTitle("KoffeinKoll Statistics");
        statisticsStage.setWidth(800);
        statisticsStage.setHeight(800);

        // Creating labels
        Label titleLabel = new Label("KoffeinKoll");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 46));
        titleLabel.setTextFill(Color.rgb(0, 70, 0));

        String styleButtons = "-fx-background-color:\n" +
                "            #090a0c,\n" +
                "            linear-gradient(#0a4a1d 0%, #8fbc8f 20%, #8fbc8f 50%, #c0dbad 100%),\n" +
                "            linear-gradient(#c0dbad, #8fbc8f),\n" +
                "            radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(black, darkgreen);\n" +
                "    -fx-font-size: 20px;\n" +
                "    -fx-padding: 10 20 10 20;" +
                "    -fx-font-weight: bold";

        JFXButton homeButton = new JFXButton("Home");
        JFXButton weeklyButton = new JFXButton("Weekly");
        JFXButton monthlyButton = new JFXButton("Monthly");
        JFXButton infoButton = new JFXButton("Statistics Info");

        homeButton.setStyle(styleButtons);
        weeklyButton.setStyle(styleButtons);
        monthlyButton.setStyle(styleButtons);
        infoButton.setStyle(styleButtons);

        // Creating a BorderPane layout for main page
        BorderPane borderPane = new BorderPane();

        // Create a VBox for the switch tile and chart
        VBox chartAndSwitchBox = new VBox();
        chartAndSwitchBox.setAlignment(Pos.CENTER);
        chartAndSwitchBox.setPadding(new Insets(10));
        chartAndSwitchBox.setSpacing(20);

        // Create a Pane for chart display
        StackPane chartPane = new StackPane();
        StapelDiagram stapelDiagram = new StapelDiagram(); // Show the stapel diagram initially
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
        buttonHBox.getChildren().addAll(homeButton, weeklyButton, monthlyButton, infoButton);
        buttonHBox.setPadding(new Insets(20));

        // Set the button box at the bottom of the BorderPane
        borderPane.setBottom(buttonHBox);

        // Create a Scene and adding the BorderPane to it
        Scene scene = new Scene(borderPane, 800, 800);

        // Setting background color as a gradient centered with yellow in the middle
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        borderPane.setBackground(new Background(new BackgroundFill(gradient, null, null)));

        // Setting the Scene to the Stage
        statisticsStage.setScene(scene);

        // Displaying the Stage
        statisticsStage.show();

        // Adding event handlers to buttons
        homeButton.setOnAction(event -> goToHomePage());
        weeklyButton.setOnAction(event -> goToWeeklyStatisticsPage());
        monthlyButton.setOnAction(event -> goToMonthlyStatisticsPage());
        infoButton.setOnAction(event -> goToInfoPage());
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
            StapelDiagram stapelDiagram = new StapelDiagram();
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
        Stage homePage = new Stage();
        HomePage homePageController = new HomePage();
        homePageController.start(homePage);
    }

    // Method to navigate to the weekly statistics page
    private void goToWeeklyStatisticsPage() {
    //add code
    }

    // Method to navigate to the monthly statistics page
    private void goToMonthlyStatisticsPage() {
        // Code to navigate to the monthly statistics page goes here
    }

    // Method to navigate to the info page
    private void goToInfoPage() {
        Stage infoPage = new Stage();
        DiagramInfoPage diagramInfoPage = new DiagramInfoPage();
        diagramInfoPage.start(infoPage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
