package KoffeinKoll.View;

import KoffeinKoll.Controller.CreateUserController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class CreateUserPage extends A_Page{
    private TextField tf_userName;
    private PasswordField pf_password;
    private TextField tf_height;
    private TextField tf_weight;
    private TextField tf_dateOfBirth;
    private JFXButton btn_createUser;
    private Label lbl_userName;
    private Label lbl_password;
    private Label lbl_passwordRequirements;
    private Label lbl_height;
    private Label lbl_weight;
    private Label lbl_dateOfBirth;

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
    }

    @Override
    public void setEvents() {
        btn_createUser.setOnAction(event -> {
            String username = tf_userName.getText();
            String password = pf_password.getText();
            String heightText = tf_height.getText();
            String weightText = tf_weight.getText();
            String dateOfBirth = tf_dateOfBirth.getText();

            // Check if any of the fields are empty
            if (username.isEmpty() || password.isEmpty() || heightText.isEmpty() || weightText.isEmpty() || dateOfBirth.isEmpty()) {
                // Display error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("All fields are required.");
                alert.show();

                // Mark empty fields with red color
                if (username.isEmpty()) tf_userName.setStyle("-fx-border-color: red;");
                if (password.isEmpty()) pf_password.setStyle("-fx-border-color: red;");
                if (heightText.isEmpty()) tf_height.setStyle("-fx-border-color: red;");
                if (weightText.isEmpty()) tf_weight.setStyle("-fx-border-color: red;");
                if (dateOfBirth.isEmpty()) tf_dateOfBirth.setStyle("-fx-border-color: red;");

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
        gridPane.add(lbl_height, 0, 4);
        gridPane.add(tf_height, 0, 5);
        gridPane.add(lbl_weight,0,6);
        gridPane.add(tf_weight,0,7);
        gridPane.add(lbl_dateOfBirth,0,8);
        gridPane.add(tf_dateOfBirth,0,9);

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
        lbl_height = setLabelStyle("Height");
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

        tf_height = setTextField();
        tf_height.setPromptText("Enter height (cm)");
        tf_weight = setTextField();
        tf_weight.setPromptText("Enter weight (kg)");
        tf_dateOfBirth = setTextField();
        tf_dateOfBirth.setPromptText("YYYY-MM-DD");
    }

    private void setButtons() {
        btn_createUser = new JFXButton("Create User");
        btn_createUser.setStyle(setButtonStyle());
    }
}
