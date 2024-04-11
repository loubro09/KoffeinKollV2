package KoffeinKoll.View;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BeverageStats extends Application {
    private Stage beverageStats;
    @Override
    public void start(Stage beverageStats) {
        this.beverageStats = beverageStats;
        beverageStats.setTitle("KoffeinKoll - Caffeine Management Tool");
        beverageStats.setWidth(800);
        beverageStats.setHeight(800);
        // Creating labels
        Label titleLabel = new Label("Log Amount");
        titleLabel.setFont(Font.font("Arial", 36));

        Label amountClLabel = new Label("Amount CL");
        amountClLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        TextField timeField = new TextField();
        timeField.setPromptText("Enter Time");
        // Creating buttons
        Button goBackButton = new Button("Go Back");
        Button homeButton = new Button("Home");

        //creating labels
        Label amountOfBeverage = new Label("Amount");
        amountOfBeverage.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label timeLabel = new Label("Time");
        timeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        TextField amountClField = new TextField();
        amountClField.setPromptText("Enter Amount in CL");

        // Definierar den färg som all rubrikstext bör ha
        Color labelColor = Color.rgb(0, 60, 0);
        timeLabel.setTextFill(labelColor); // Keeping the color scheme consistent
        titleLabel.setTextFill(labelColor);
        amountClLabel.setTextFill(labelColor);
        amountOfBeverage.setTextFill(labelColor);


        //textrutorna
        TextField amountField = textField();
        amountField.setPromptText("Enter Amount");

        String styleButtons = "-fx-background-color:\n" +
                "            #090a0c,\n" +
                "            linear-gradient(#8fbc8f 0%, #8fbc8f 20%, #8fbc8f 100%),\n" +
                "            linear-gradient(#c0dbad, #8fbc8f),\n" +
                "            radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(black, black);\n" +
                "    -fx-font-size: 20px;\n" +
                "    -fx-padding: 10 20 10 20;";

        Button logAmount = new Button("Log Amount");
        goBackButton.setStyle(styleButtons);
        homeButton.setStyle(styleButtons);
        logAmount.setStyle(styleButtons);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(amountOfBeverage, 0, 0); // Add new label and field beverage
        gridPane.add(amountField, 1, 0);

        gridPane.add(amountClLabel, 0, 1); // Add new label and field for Amount CL
        gridPane.add(amountClField, 1, 1);

        gridPane.add(timeLabel, 0, 2); // Add new label and field for Time
        gridPane.add(timeField, 1, 2);

        gridPane.add(logAmount, 0, 3, 2, 1); // Spanning two columns for centering
        gridPane.setHalignment(logAmount, HPos.CENTER);


        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(gridPane);


        // Creating a BorderPane layout for main page
        BorderPane borderPane1 = new BorderPane();
        borderPane1.setPadding(new Insets(20));
        borderPane1.setCenter(borderPane);


        // Creating a HBox for buttons
        HBox buttonHBox = new HBox(20);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(goBackButton, homeButton);
        borderPane1.setBottom(buttonHBox);

        // Creating a VBox for main page
        HBox topHBox = new HBox();
        topHBox.getChildren().add(titleLabel);
        topHBox.setAlignment(Pos.CENTER);
        borderPane1.setTop(topHBox);


        // Creating a Scene and adding the BorderPane to it
        Scene scene = new Scene(borderPane1, 800, 800);

        // Setting background color as a gradient centered with yellow in the middle
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        borderPane1.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        homeButton.setOnAction(e -> {
            goToHomePage();
        });


        // Displaying the Stage, Setting the Scene to the Stage
        beverageStats.setScene(scene);
        beverageStats.show();
    }
    private TextField textField(){
        TextField fieldStyle = new TextField();
        fieldStyle.setFont(Font.font("Arial", 14));
        fieldStyle.setPrefWidth(220);
        fieldStyle.setPrefHeight(30);
        fieldStyle.setStyle(" -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        return fieldStyle;
    }
    private void goToHomePage() {
        HomePage homePage = new HomePage();
        homePage.start(beverageStats);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

