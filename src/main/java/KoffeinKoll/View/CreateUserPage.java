package KoffeinKoll.View;

import KoffeinKoll.Controller.CreateUserController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    private JFXButton btn_goBack;
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
     *
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
     *
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
     *
     * @author Kenan Al-tal, Alanah Coleman, Louis Brown                                                                                      //AUTHOR
     */
    @Override
    public void setEvents() {
        btn_goBack.setOnAction(e -> goBack());
        btn_createUser.setOnAction(event -> {
            String username = tf_userName.getText();
            String password = pf_password.getText();
            String habit = habitValue();
            String weightText = tf_weight.getText();
            weightText = weightText.replace(",", ".");
            LocalDate dateOfBirth = datePicker.getValue();


            if (username.isEmpty() || password.isEmpty() || weightText.isEmpty() || dateOfBirth == null || habit == null) {
                showAlert("Error", "All fields are required.", Alert.AlertType.ERROR);

                //Mark empty fields with red color
                if (username.isEmpty()) tf_userName.setStyle("-fx-border-color: red;");
                if (password.isEmpty()) pf_password.setStyle("-fx-border-color: red;");
                if (weightText.isEmpty()) tf_weight.setStyle("-fx-border-color: red;");
                if (dateOfBirth == null) datePicker.setStyle("-fx-border-color: red;");
                if (habit == null) lbl_habit.setStyle("-fx-border-color: red;");

                return;
            }

            if (username.contains(" ")) {
                showAlert("Error", "Username cannot contain spaces.", Alert.AlertType.ERROR);
                return;
            }

            if (password.contains(" ")) {
                showAlert("Error", "Password cannot contain spaces.", Alert.AlertType.ERROR);
                return;
            }

            if (!checkAge(dateOfBirth)) {
                showAlert("Error", "You have to be at least 15 years of age to use this application.", Alert.AlertType.ERROR);
                return;
            }

            double weight = 0;
            if (weightText.matches("\\d*\\.?\\d+")) {
                weight = Double.parseDouble(weightText);
                if (weight == 0) {
                    showAlert("Error", "You cannot weigh 0 kg.", Alert.AlertType.ERROR);
                    return;
                }
            } else {
                showAlert("Error", "Invalid weight input! Please try again.", Alert.AlertType.ERROR);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateOfBirthText = dateOfBirth.format((formatter));

            CreateUserController createUserController = new CreateUserController();

            boolean userCreated = createUserController.createUser(username, password, habit, weight, dateOfBirthText);


            if (userCreated) {
                showAlert("Success", "User created successfully!", Alert.AlertType.INFORMATION);
                changePage(new LogInPage());
            }
        });
    }

    /**
     * Sets the scene layout for the create user page.
     *
     * @author Ida Nordenswan
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
        gridPane.add(lbl_passwordRequirements, 0, 4);
        gridPane.add(lbl_habit, 0, 5);
        gridPane.add(rb_option1, 0, 6);
        gridPane.add(rb_option2, 0, 7);
        gridPane.add(rb_option3, 0, 8);
        gridPane.add(lbl_weight, 0, 9);
        gridPane.add(tf_weight, 0, 10);
        gridPane.add(lbl_dateOfBirth, 0, 11);
        gridPane.add(datePicker, 0, 12);
        HBox buttonBox = new HBox(20, btn_createUser, btn_goBack);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 14);

        borderPane.setPadding(new Insets(20));
        borderPane.setTop(lbl_title);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(lbl_title, Pos.CENTER);
    }

    /**
     * Sets labels for UI elements.
     *
     * @author Ida Nordenswan
     */
    private void setLabels() {
        lbl_userName = setLabelStyle("Username");
        lbl_password = setLabelStyle("Password");
        lbl_habit = setLabelStyle("How often do you consume caffeine drinks per day?");
        lbl_weight = setLabelStyle("Weight");
        lbl_dateOfBirth = setLabelStyle("Date of Birth");
        lbl_passwordRequirements = setLabelStyle("Password must contain at least 8 characters, one capital letter, and one number.");
        lbl_passwordRequirements.setFont(Font.font("Arial", 12));


        Tooltip tooltip1 = new Tooltip("\n" +
                "Consistently consuming caffeine builds tolerance over time, enhancing your body's ability to handle it. \n");
        tooltip1.setShowDelay(Duration.millis(10));
        tooltip1.setShowDuration(Duration.seconds(5));
        Tooltip.install(lbl_habit, tooltip1);
    }

    /**
     * Sets text fields for UI elements.
     *
     * @author Ida Nordenswan
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
     *
     * @author Ida Nordenswan
     */
    private void setButtons() {
        btn_createUser = new JFXButton("Create User");
        btn_createUser.setStyle(setButtonStyle());

        btn_goBack = new JFXButton("Go Back");
        btn_goBack.setStyle(setButtonStyle());
    }

    /**
     * Sets radio buttons for UI elements.
     *
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
     * Sets date picker for UI elements.
     *
     * @author Alanah Coleman
     */
    private void setDatePicker() {
        datePicker = new DatePicker();
        datePicker.setPromptText("YYYY-MM-DD");
    }

    private void goBack() {
        changePage(new LogInPage());
    }

    /**
     * Retrieves the selected habit value from the radio button group.
     *
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
     * Checks if the chosen date is at least fifteen years ago.
     *
     * @param chosenDate The chosen date of birth.
     * @return True if the chosen date is at least fifteen years ago, false otherwise.
     * @author Louis Brown
     */
    private boolean checkAge(LocalDate chosenDate) {
        LocalDate currentDate = LocalDate.now();

        LocalDate fifteenYearsAgo = currentDate.minusYears(15);

        return chosenDate.isBefore(fifteenYearsAgo) || chosenDate.isEqual(fifteenYearsAgo);
    }
}