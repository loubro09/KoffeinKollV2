package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class BeverageMenuPage extends Application {
    private Stage menuStage;
    private Map<String, Integer> beverageIdMap;

    @Override
    public void start(Stage menuStage) {
        this.menuStage = menuStage;

        // Set the title for the window (stage)
        menuStage.setTitle("KoffeinKoll - Caffeine Management Tool");
        menuStage.setWidth(800);  // Set the width of the window
        menuStage.setHeight(800); // Set the height of the window

        // Create a label for the main title
        Label titleLabel = new Label("KoffeinKoll");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 46));

        // Create a label for the subtitle
        Label littleTitleLabel = new Label("  Let's get started!\nChoose your drink:");
        littleTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 23));

        // Define the color for the labels
        Color labelColor = Color.rgb(0, 60, 0);
        titleLabel.setTextFill(labelColor);
        littleTitleLabel.setTextFill(labelColor);

        // Create the buttons for each beverage
        JFXButton coffeeButton = createBeverageButton("Regular Coffee");
        JFXButton espresso1Button = createBeverageButton("Single Shot Espresso");
        JFXButton espresso2Button = createBeverageButton("Double Shot Espresso");
        JFXButton teaButton = createBeverageButton("Tea");
        JFXButton mateButton = createBeverageButton("Mate");
        JFXButton energyDrinkButton = createBeverageButton("Energy drink");
        JFXButton sodaButton = createBeverageButton("Soda");

        // Create a button to go back
        JFXButton goBackButton = createBeverageButton("Go Back");

        // Initialize the mapping of beverage names to their IDs
        beverageIdMap = new HashMap<>();
        beverageIdMap.put("Regular Coffee", 1); // Use the actual IDs from your beverages table
        beverageIdMap.put("Single Shot Espresso", 2);
        // ... add the rest of your beverages here ...

        // Set up a GridPane to hold the buttons and labels
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.add(littleTitleLabel, 0, 0);
        GridPane.setHalignment(littleTitleLabel, HPos.CENTER);
        // Add the buttons to the grid
        gridPane.add(coffeeButton, 0, 1);
        gridPane.add(espresso1Button, 0, 2);
        gridPane.add(espresso2Button, 0, 3);
        gridPane.add(teaButton, 0, 4);
        gridPane.add(mateButton, 0, 5);
        gridPane.add(energyDrinkButton, 0, 6);
        gridPane.add(sodaButton, 0, 7);

        // Create the main layout pane and add components
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        // Create a horizontal box for the 'Go Back' button
        HBox buttonHBox = new HBox(20);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(goBackButton);
        borderPane.setBottom(buttonHBox);

        // Create a gradient background
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        // Set the scene and show the stage
        Scene scene = new Scene(borderPane, 800, 800);
        menuStage.setScene(scene);
        menuStage.show();

        // Assign actions to the 'Go Back' button
        goBackButton.setOnAction(e -> goBack());
    }

    // Method to create a button and set its style
    private JFXButton createBeverageButton(String beverageName) {
        JFXButton button = new JFXButton(beverageName);
        button.setStyle(koffeinKollButtonStyle());
        button.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHalignment(button, HPos.CENTER);
        if (!beverageName.equals("Go Back")) {
            // If it's a beverage button, assign an action to handle beverage selection
            button.setOnAction(e -> handleBeverageSelection(beverageName));
        }
        return button;
    }

    // Method to handle beverage selection
    private void handleBeverageSelection(String beverageName) {
        int beverageId = beverageIdMap.get(beverageName);
        menuStage.close(); // Close the current stage
        BeverageStats beverageStats = new BeverageStats(beverageId); // Modify BeverageStats to accept an ID
        Stage beverageStatsStage = new Stage();
        beverageStats.start(beverageStatsStage); // Start the BeverageStats stage
    }

    // Method to define the button style
    private String koffeinKollButtonStyle() {
        return "-fx-background-color: #090a0c, linear-gradient(#0a4a1d 0%, #8fbc8f 20%, #8fbc8f 50%, #c0dbad 100%), linear-gradient(#c0dbad, #8fbc8f), radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0)); -fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0; -fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-font-family: \"Arial\"; -fx-text-fill: linear-gradient(black, darkgreen); -fx-font-size: 20px; -fx-padding: 10 20 10 20; -fx-font-weight: bold";
    }

    // Method to go back to the previous page
    private void goBack() {
        HomePage homePage = new HomePage();
        homePage.start(menuStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}



