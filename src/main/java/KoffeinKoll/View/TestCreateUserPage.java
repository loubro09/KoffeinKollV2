package KoffeinKoll.View;

import KoffeinKoll.Controller.CreateUserController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TestCreateUserPage extends A_Page{
    private TextField userNameField;
    private PasswordField passwordField;
    private TextField heightField;
    private TextField weightField;
    private TextField dateOfBirthField;
    private JFXButton createUserButton;
    private Label userNameLabel;
    private Label passwordLabel;
    private Label passwordRequirementsLabel;
    private Label heightLabel;
    private Label weightLabel;
    private Label dateOfBirthLabel;


    public TestCreateUserPage(Stage stage) {
        this.stage = stage;
        initialPage(stage);
    }

    @Override
    public void initializeUI() {
        setTitle();
        //Rubrik ovanför textrutor
        userNameLabel = new Label("Username");
        userNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //Height
        heightLabel = new Label("Height");
        heightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //Weight
        weightLabel = new Label("Weight");
        weightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //Date of birth
        dateOfBirthLabel = new Label("Date of Birth");
        dateOfBirthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        passwordRequirementsLabel = new Label("Password must contain at least 8 characters, one capital letter, and one number.");
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

        userNameField = setTextField();
        userNameField.setPromptText("Enter a username");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter a password ");

        // To mach the textbox and text size to the TextField
        passwordField.setPrefWidth(220);
        passwordField.setPrefHeight(30);
        passwordField.setStyle("-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-padding: 5px;");
        passwordField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%); -fx-font: 14px \"Arial\";");

        heightField = setTextField();
        heightField.setPromptText("Enter height (cm)");
        weightField = setTextField();
        weightField.setPromptText("Enter weight (kg)");
        dateOfBirthField = setTextField();
        dateOfBirthField.setPromptText("YYYY-MM-DD");

        createUserButton = new JFXButton("Create User");
        createUserButton.setStyle(setButtonStyle());

        initializeEventHandler();
    }

    @Override
    public void createScene(BorderPane borderPane) {
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


        borderPane.setPadding(new Insets(20));
        borderPane.setTop(titleLabel);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
    }

    private void initializeEventHandler () {
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
            boolean userCreated = createUserController.createUser(username, password, height, weight, dateOfBirth);

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
    }
}
