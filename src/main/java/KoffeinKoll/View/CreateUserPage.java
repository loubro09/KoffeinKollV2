package KoffeinKoll.View;

import KoffeinKoll.Controller.CreateUserController;
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

public class CreateUserPage extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;

        stage.setTitle("KoffeinKoll - Caffeine Management Tool");
        stage.setWidth(800);
        stage.setHeight(800);

        //Huvudtitel
        Label titleLabel = new Label("KoffeinKoll");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 46));
        titleLabel.setTextFill(Color.rgb(0, 60, 0));


        //Rubrik ovanför textrutor
        Label userNameLabel = new Label("Username");
        userNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //Height
        Label heightLabel = new Label("Height");
        heightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));


        //Weight
        Label weightLabel = new Label("Weight");
        weightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //Date of birth
        Label dateOfBirthLabel = new Label("Date of Birth");
        dateOfBirthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label passwordRequirementsLabel = new Label("Password must contain at least 8 characters, one capital letter, and one number.");
        passwordRequirementsLabel.setFont(Font.font("Arial", 12));
        passwordRequirementsLabel.setTextFill(Color.rgb(0, 60, 0)); // You can adjust the color as needed



        // Definierar den färg som all rubrikstext bör ha
        Color labelColor = Color.rgb(0, 60, 0);
        titleLabel.setTextFill(labelColor);
        userNameLabel.setTextFill(labelColor);
        passwordLabel.setTextFill(labelColor);
        heightLabel.setTextFill(labelColor);
        weightLabel.setTextFill(labelColor);
        dateOfBirthLabel.setTextFill(labelColor);

        //textrutorna

        TextField userNameField = textField();
        userNameField.setPromptText("Enter a username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter a password ");
        // To mach the textbox and text size to the TextField
        passwordField.setPrefWidth(220);
        passwordField.setPrefHeight(30);
        passwordField.setStyle("-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-padding: 5px;");
        passwordField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%); -fx-font: 14px \"Arial\";");

        TextField heightField = textField();
        heightField.setPromptText("Enter height (cm)");
        TextField weightField = textField();
        weightField.setPromptText("Enter weight (kg)");
        TextField dateOfBirthField = textField();
        dateOfBirthField.setPromptText("YYYY-MM-DD");





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

        Button createUserButton = new Button("Create User");
        createUserButton.setStyle(styleButtons);

        createUserButton.setOnAction(event -> {
            String username = userNameField.getText();
            String password = passwordField.getText();
            String heightText = heightField.getText();
            String weightText = weightField.getText();
            String dateOfBirth = dateOfBirthField.getText();

            // Check if any of the fields are empty
            if (username.isEmpty() || password.isEmpty() || heightText.isEmpty() || weightText.isEmpty() || dateOfBirth.isEmpty()) {
                // Display error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("All fields are required.");
                alert.show();


                // Mark empty fields with red color
                if (username.isEmpty()) userNameField.setStyle("-fx-border-color: red;");
                if (password.isEmpty()) passwordField.setStyle("-fx-border-color: red;");
                if (heightText.isEmpty()) heightField.setStyle("-fx-border-color: red;");
                if (weightText.isEmpty()) weightField.setStyle("-fx-border-color: red;");
                if (dateOfBirth.isEmpty()) dateOfBirthField.setStyle("-fx-border-color: red;");

                return; // Stop further processing
            }

            // Convert height and weight to double
            double height = Double.parseDouble(heightText);
            double weight = Double.parseDouble(weightText);

            CreateUserController createUserController = new CreateUserController();

            //FIX THE USER ID IN THE DATABASE
            boolean userCreated = createUserController.createUser(4,username, password, height, weight, dateOfBirth);

            if (userCreated) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("User created successfully!");
                successAlert.showAndWait();
            } else {
                // Display error message in a popup
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to create user.");
                errorAlert.showAndWait();
            }
        });



        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(userNameLabel, 0, 0);
        gridPane.add(userNameField, 0, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 0, 3);
        gridPane.add(heightLabel, 0, 4);
        gridPane.add(heightField, 0, 5);
        gridPane.add(weightLabel,0,6);
        gridPane.add(weightField,0,7);
        gridPane.add(dateOfBirthLabel,0,8);
        gridPane.add(dateOfBirthField,0,9);

        gridPane.add(createUserButton, 0, 13); // Remove this line
        gridPane.add(passwordRequirementsLabel, 0, 15);

        gridPane.setHalignment(createUserButton, Pos.CENTER.getHpos());

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

        // Setting the Scene to the Stage
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