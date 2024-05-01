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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * CreateUserPage represents the user interface for creating a new user account in the KoffeinKoll application.
 * It allows users to enter their desired username, password, weight, date of birth, and caffeine consumption habit,
 * and provides functionality for creating the user account.
 */
public class CreateUserPage extends A_Page {
    private TextField tf_userName;
    private PasswordField pf_password;
    private TextField tf_weight;
    private JFXButton btn_createUser;
    private Label lbl_userName;
    private Label lbl_password;
    private Label lbl_passwordRequirements;
    private Label lbl_habit;
    private Label lbl_weight;
    private Label lbl_dateOfBirth;
    private ToggleGroup toggleGroup;
    private RadioButton rb_option1;
    private RadioButton rb_option2;
    private RadioButton rb_option3;
    private DatePicker datePicker;


    /**
     * Initializes the UI components of the create user page.
     * @author Louis Brown
     */
    @Override
    public void initializeUI() {
        setComponents();
        setEvents();
        setScene();
    }

    /**
     * Sets the UI components for the create user page.
     * @author Louis Brown
     */
    @Override
    public void setComponents() {
        setLabels();
        setTextfields();
        setButtons();
        setRadioButton();
        setDatePicker();
    }

    /**
     * Sets the event handlers for UI controls on the create user page.
     * @author                                                                                          //AUTHOR
     */
    @Override
    public void setEvents() {
        btn_createUser.setOnAction(event -> {
            String username = tf_userName.getText();
            String password = pf_password.getText();
            String habit = habitValue();
            String weightText = tf_weight.getText();
            LocalDate dateOfBirth = datePicker.getValue();


            //Check if any of the fields are empty
            if (username.isEmpty() || password.isEmpty() || weightText.isEmpty() || dateOfBirth==null || habit == null) {
                //Display error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("All fields are required.");
                alert.show();

                //Mark empty fields with red color
                if (username.isEmpty()) tf_userName.setStyle("-fx-border-color: red;");
                if (password.isEmpty()) pf_password.setStyle("-fx-border-color: red;");
                if (weightText.isEmpty()) tf_weight.setStyle("-fx-border-color: red;");
                if (dateOfBirth==null) datePicker.setStyle("-fx-border-color: red;");
                if (habit == null) lbl_habit.setStyle("-fx-border-color: red;");

                return; //Stop further processing
            }

            if (!isAtLeastFifteenYearsAgo(dateOfBirth)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You have to be at least 15 years of age to use this application.");
                alert.show();
                return; //Stop further processing
            }

            double weight = Double.parseDouble(weightText);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateOfBirthText = dateOfBirth.format((formatter));

            CreateUserController createUserController = new CreateUserController();

            boolean userCreated = createUserController.createUser(username, password, habit, weight, dateOfBirthText);

            //Displays success message
            if (userCreated) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("User created successfully!");
                successAlert.showAndWait();
                changePage(new LogInPage()); //opens login page
            }
        });
    }

    /**
     * Sets the scene layout for the create user page.
     * @author                                                                                          //AUTHOR
     */
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
        gridPane.add(rb_option1, 0, 5);
        gridPane.add(rb_option2, 0, 6);
        gridPane.add(rb_option3, 0, 7);
        gridPane.add(lbl_weight, 0, 8);
        gridPane.add(tf_weight, 0, 9);
        gridPane.add(lbl_dateOfBirth, 0, 10);
        gridPane.add(datePicker, 0, 11);
        gridPane.add(btn_createUser, 0, 13); // Remove this line
        gridPane.add(lbl_passwordRequirements, 0, 15);

        gridPane.setHalignment(btn_createUser, Pos.CENTER.getHpos());

        borderPane.setPadding(new Insets(20));
        borderPane.setTop(lbl_title);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(lbl_title, Pos.CENTER);
    }

    /**
     * Sets labels for UI elements.
     * @author                                                                                          //AUTHOR
     */
    private void setLabels() {
        lbl_userName = setLabelStyle("Username");
        lbl_password = setLabelStyle("Password");
        lbl_habit = setLabelStyle("Habit");
        lbl_weight = setLabelStyle("Weight");
        lbl_dateOfBirth = setLabelStyle("Date of Birth");

        lbl_passwordRequirements = setLabelStyle("Password must contain at least 8 characters, one capital letter, and one number.");
        lbl_passwordRequirements.setFont(Font.font("Arial", 12));
    }

    /**
     * Sets text fields for UI elements.
     * @author                                                                                          //AUTHOR
     */
    private void setTextfields() {
        tf_userName = setTextField();
        tf_userName.setPromptText("Enter a username");

        pf_password = setPasswordField();
        pf_password.setPromptText("Enter a password ");

        tf_weight = setTextField();
        tf_weight.setPromptText("Enter weight (kg)");
    }

    /**
     * Sets buttons for UI elements.
     * @author                                                                                          //AUTHOR
     */
    private void setButtons() {
        btn_createUser = new JFXButton("Create User");
        btn_createUser.setStyle(setButtonStyle());
    }

    /**
     * Sets radio buttons for UI elements.
     * @author Louis Brown
     */
    private void setRadioButton() {
        toggleGroup = new ToggleGroup();

        rb_option1 = new RadioButton("0-1");
        rb_option1.setToggleGroup(toggleGroup);
        rb_option1.setSelected(true); //default value

        rb_option2 = new RadioButton("1-2");
        rb_option2.setToggleGroup(toggleGroup);

        rb_option3 = new RadioButton("2-5");
        rb_option3.setToggleGroup(toggleGroup);
    }

    /**
     * Retrieves the selected habit value from the radio button group.
     * @return The selected habit value as a String, or null if no radio button is selected.
     * @author Louis Brown
     */
    private String habitValue() {
        if (toggleGroup.getSelectedToggle() != null) {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            return selectedRadioButton.getText();
        }
        return null;
    }

    /**
     * Sets date picker for UI elements.
     * @author Alanah Coleman
     */
    private void setDatePicker() {
        datePicker = new DatePicker();
        datePicker.setPromptText("Select Date of Birth");
    }

    /**
     * Checks if the chosen date is at least fifteen years ago.
     * @param chosenDate The chosen date of birth.
     * @return True if the chosen date is at least fifteen years ago, false otherwise.
     * @author Louis Brown
     */
    private boolean isAtLeastFifteenYearsAgo(LocalDate chosenDate) {
        //Get the current date
        LocalDate currentDate = LocalDate.now();

        //Calculate the date 15 years ago
        LocalDate fifteenYearsAgo = currentDate.minusYears(15);

        //Check if the chosen date is at least 15 years ago
        return chosenDate.isBefore(fifteenYearsAgo) || chosenDate.isEqual(fifteenYearsAgo);
    }
}