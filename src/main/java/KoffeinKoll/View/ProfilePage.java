package KoffeinKoll.View;

import KoffeinKoll.Controller.ProfileController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
public class ProfilePage extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;

        stage.setTitle("KoffeinKoll - Update User Information");
        stage.setWidth(800);
        stage.setHeight(800);

        //Huvudtitel
        Label titleLabel = new Label("KoffeinKoll - Update User Information");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titleLabel.setTextFill(Color.rgb(0, 60, 0));

        //textrutorna to enter new values
        TextField newHeightField = textField();
        newHeightField.setPromptText("Height (cm)");
        Label newHeightTextLabel = new Label("New Height:");
        newHeightTextLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        newHeightTextLabel.setTextFill(Color.rgb(0, 60, 0));

        TextField newWeightField = textField();
        newWeightField.setPromptText("Weight (kg)");
        Label newWeightTextLabel = new Label("New Weight:");
        newWeightTextLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        newWeightTextLabel.setTextFill(Color.rgb(0, 60, 0));

        TextField newDateOfBirthField = textField();
        newDateOfBirthField.setPromptText("YYYY-MM-DD");
        Label newDateOfBirthTextLabel = new Label("New Date of Birth:");
        newDateOfBirthTextLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        newDateOfBirthTextLabel.setTextFill(Color.rgb(0, 60, 0));

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

        Button backButton = new Button("Back");
        backButton.setStyle(styleButtons);
        backButton.setOnAction(event -> {
            // Code to go back a step
        });


        Button saveButton = new Button("Save");
        saveButton.setStyle(styleButtons);
        saveButton.setOnAction(event -> {
            String newHeightText = newHeightField.getText();
            String newWeightText = newWeightField.getText();
            String newDateOfBirth = newDateOfBirthField.getText();

            // Validate input and save new user information
            // Implement your validation and saving logic here

            // Display success or error message accordingly
        });

        ProfileController profileController = new ProfileController();

        saveButton.setOnAction(event -> {
            String newHeightText = newHeightField.getText();
            String newWeightText = newWeightField.getText();
            String newDateOfBirth = newDateOfBirthField.getText();

            // Validate input and save new user information
            boolean userUpdated = profileController.updateUser(2, newHeightText, newWeightText, newDateOfBirth);

            // Display success or error message accordingly
            if (userUpdated) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("User information updated successfully!");
                successAlert.showAndWait();
            } else {
                // Display error message in a popup
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to update user information.");
                errorAlert.showAndWait();
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(newHeightTextLabel, 0, 0);
        gridPane.add(newHeightField, 1, 0);
        gridPane.add(newWeightTextLabel, 0, 1);
        gridPane.add(newWeightField, 1, 1);
        gridPane.add(newDateOfBirthTextLabel, 0, 2);
        gridPane.add(newDateOfBirthField, 1, 2);
        gridPane.add(backButton, 0, 6);
        gridPane.add(saveButton, 1, 6);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setTop(titleLabel);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        Scene scene = new Scene(borderPane, 800, 800);

        // Setting background color as a gradient centered with yellow in the middle
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        stage.setScene(scene);
        stage.show();


    }

    private TextField textField(){
        TextField fieldStyle = new TextField();
        fieldStyle.setFont(Font.font("Arial", 14));
        fieldStyle.setPrefWidth(220);
        fieldStyle.setPrefHeight(30);
        fieldStyle.setStyle(" -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        return fieldStyle;
    }
    public static void main(String[] args) {
        launch(args);
    }

}

