package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import javafx.scene.text.Text;
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
        titleLabel.setFont(Font.font("Arial",FontWeight.BOLD, 46));

        // Creating buttons
        JFXButton goBackButton = new JFXButton("Go Back");
        JFXButton homeButton = new JFXButton("Home");
        JFXButton logAmount = new JFXButton("Log Amount");

        //creating labels
        Label amountClLabel = new Label("Amount CL");
        amountClLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
        Label amountOfBeverage = new Label("Amount");
        amountOfBeverage.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label timeLabel = new Label("Time");
        timeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Definierar den färg som all rubrikstext bör ha
        Color labelColor = Color.rgb(0, 60, 0);
        timeLabel.setTextFill(labelColor); // Keeping the color scheme consistent
        titleLabel.setTextFill(labelColor);
        amountClLabel.setTextFill(labelColor);
        amountOfBeverage.setTextFill(labelColor);

        //textrutorna
        TextField amountField = textField();
        amountField.setPromptText("Enter Amount");

        TextField amountClField = textField();
        amountClField.setPromptText("Enter Amount in CL");

        TextField timeField = textField();
        timeField.setPromptText("Enter Time");


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
                "    -fx-padding: 10 20 10 20;"+
                "    -fx-font-weight: bold";

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

        // Buttons to go back one step and to go to homepage
        homeButton.setOnAction(e -> {
            goToHomePage();
        });
        goBackButton.setOnAction(e -> {
            goBack();
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
    private void goBack() {
        BevarageMenuPage bevarageMenuPage = new BevarageMenuPage();
        bevarageMenuPage.start(beverageStats);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

