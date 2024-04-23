package KoffeinKoll.View;

import KoffeinKoll.Controller.LoginController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TestLoginPage extends A_Page{
    private Label titleLabel;
    private Label userNameLabel;
    private Label passwordLabel;
    private TextField userNameField;
    private PasswordField passwordField;
    private Hyperlink registration;
    private JFXButton logInButton;

    @Override
    public void initializeUI() {
        titleLabel = new Label("KoffeinKoll");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 46));

        //Rubrik ovanför textrutor
        userNameLabel = new Label("Username");
        userNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Definierar den färg som all rubrikstext bör ha
        Color labelColor = Color.rgb(0, 60, 0);
        titleLabel.setTextFill(labelColor);
        userNameLabel.setTextFill(labelColor);
        passwordLabel.setTextFill(labelColor);

        //textrutorna

        userNameField = textField();
        userNameField.setPromptText("Enter username");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password ");

        logInButton = new JFXButton("Log in");
        logInButton.setStyle(setButtonStyle());

        passwordField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                logInButton.fire();
                ev.consume();
            }
        });

        registration = new Hyperlink("Not registered? Create an account!");
        registration.setFont(Font.font("Arial", 14));

        initializeEventHandlers();
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
        gridPane.add(registration,0,5);
        gridPane.add(logInButton, 0, 7);
        gridPane.setHalignment(logInButton, Pos.CENTER.getHpos());


        borderPane = getBorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(gridPane);

        // Creating a VBox for main page
        HBox topHBox = new HBox();
        topHBox.getChildren().add(titleLabel);
        topHBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topHBox);
    }

    private TextField textField(){
        TextField fieldStyle = new TextField();
        fieldStyle.setFont(Font.font("Arial", 14));
        fieldStyle.setPrefWidth(220);
        fieldStyle.setPrefHeight(30);
        fieldStyle.setStyle(" -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        return fieldStyle;
    }

    private void initializeEventHandlers() {
        // Log in button action handler
        logInButton.setOnAction(event -> handleLogin());

        // Handle enter key press in password field
        passwordField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                handleLogin();
                ev.consume();
            }
        });

        // Registration link action handler
        registration.setOnAction(event -> {
            // Handle registration link click here
        });
    }
    private void handleLogin() {
        String username = userNameField.getText();
        String password = passwordField.getText();

        // Logic for handling login here
        LoginController loginController = new LoginController();
        boolean loggedIn = loginController.logIn(username, password);

        if (loggedIn) {
            // If login successful, you might want to switch to another page
            changePage(new TestHomePage());
        } else {
            System.out.println("Login failed");
        }
    }
}
