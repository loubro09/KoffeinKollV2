package KoffeinKoll.View;

import KoffeinKoll.Controller.CreateUserController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.DatePicker;

import java.util.Enumeration;


public class CreateUserPage extends A_Page {
    private TextField tf_userName;
    private PasswordField pf_password;
    private TextField tf_weight;
    private TextField tf_dateOfBirth;
    private JFXButton btn_createUser;
    private Label lbl_userName;
    private Label lbl_password;
    private Label lbl_passwordRequirements;
    private Label lbl_habit;
    private Label lbl_weight;
    private Label lbl_dateOfBirth;
    private ToggleGroup toggleGroup;
    private DatePicker datePicker;


    @Override
    public void initializeUI() {
        setComponents();
        setEvents();
        setScene();
    }

    @Override
    public void setComponents() {
        setLabels();
        setTextfields();
        setButtons();
        setRadioButton();
    }

    @Override
    public void setEvents() {
        btn_createUser.setOnAction(event -> {
            String username = tf_userName.getText();
            String password = pf_password.getText();

            //hämta radiobutton
            String weightText = tf_weight.getText();
            String dateOfBirth = tf_dateOfBirth.getText();

            // Check if any of the fields are empty
            if (username.isEmpty() || password.isEmpty() || weightText.isEmpty() || dateOfBirth.isEmpty()) {
                // Display error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("All fields are required.");
                alert.show();

                // Mark empty fields with red color
                if (username.isEmpty()) tf_userName.setStyle("-fx-border-color: red;");
                if (habit.)
                    if (password.isEmpty()) pf_password.setStyle("-fx-border-color: red;");
                if (weightText.isEmpty()) tf_weight.setStyle("-fx-border-color: red;");
                if (dateOfBirth.isEmpty()) tf_dateOfBirth.setStyle("-fx-border-color: red;");

                return; // Stop further processing
            }

            // Convert height and weight to double
            double weight = Double.parseDouble(weightText);

            CreateUserController createUserController = new CreateUserController();

            //FIX THE USER ID IN THE DATABASE
            boolean userCreated = createUserController.createUser(username, password, habit, weight, dateOfBirth);

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

    @Override
    public void setScene() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(lbl_userName, 0, 0);
        gridPane.add(tf_userName, 0, 1);
        gridPane.add(lbl_password, 0, 2);
        gridPane.add(pf_password, 0, 3);
        gridPane.add(lbl_habit, 0, 4);
      //  gridPane.add(toggleGroup, 0, 5);
        gridPane.add(lbl_weight, 0, 6);
        gridPane.add(tf_weight, 0, 7);
        gridPane.add(lbl_dateOfBirth, 0, 8);
        //gridPane.add(tf_dateOfBirth,0,9);
        gridPane.add(datePicker, 0, 8);

        gridPane.add(btn_createUser, 0, 13); // Remove this line
        gridPane.add(lbl_passwordRequirements, 0, 15);

        gridPane.setHalignment(btn_createUser, Pos.CENTER.getHpos());

        //BorderPane borderPane = new BorderPane();
        //borderPane = getBorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setTop(lbl_title);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(lbl_title, Pos.CENTER);
    }

    private void setLabels() {
        lbl_userName = setLabelStyle("Username");
        lbl_password = setLabelStyle("Password");
        lbl_habit = setLabelStyle("Habit");
        lbl_weight = setLabelStyle("Weight");
        lbl_dateOfBirth = setLabelStyle("Date of Birth");
        lbl_passwordRequirements = setLabelStyle("Password must contain at least 8 characters, one capital letter, and one number.");
        lbl_passwordRequirements.setFont(Font.font("Arial", 12));
    }

    private void setTextfields() {
        tf_userName = setTextField();
        tf_userName.setPromptText("Enter a username");
        pf_password = setPasswordField();
        pf_password.setPromptText("Enter a password ");

        tf_weight = setTextField();
        tf_weight.setPromptText("Enter weight (kg)");
        //  tf_dateOfBirth = setTextField();
        // tf_dateOfBirth.setPromptText("YYYY-MM-DD");
        datePicker = new DatePicker(); // Skapa DatePicker-instans
        datePicker.setPromptText("Select Date of Birth"); // Användarinformation
    }

    private void setButtons() {
        btn_createUser = new JFXButton("Create User");
        btn_createUser.setStyle(setButtonStyle());
    }

    private void setRadioButton() {
        toggleGroup = new ToggleGroup();

        // Create radio buttons
        RadioButton option1 = new RadioButton("0-1");
        option1.setToggleGroup(toggleGroup);
        option1.setUserData("1");

        RadioButton option2 = new RadioButton("1-2");
        option2.setToggleGroup(toggleGroup);
        option2.setUserData("2");

        RadioButton option3 = new RadioButton("2-5");
        option3.setToggleGroup(toggleGroup);
        option3.setUserData("3");
    }

    private int habitValue() {
        int value = 0; // Initialize value to a default value
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                String selectedOption = (String) selectedRadioButton.getUserData();

                System.out.println("Selected: " + selectedRadioButton.getText());
                if ("1".equals(selectedOption)) {
                    value = 1;
                } else if ("2".equals(selectedOption)) {
                    value = 2;
                } else if ("3".equals(selectedOption)) {
                    value = 3;
                }
            }
        });
        return value;
    }
}