package KoffeinKoll.View;

import KoffeinKoll.Controller.ProfileController;
import KoffeinKoll.Controller.UserController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.controlsfx.control.ToggleSwitch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * ProfilePage class represents the user profile page where users can update their information.
 * It extends A_Page and implements methods to initialize UI components, set event handlers, and set the scene.
 */
public class ProfilePage extends A_Page {
    private Label lbl_newHabit;
    private Label lbl_newWeight;
    private Label lbl_newDateOfBirth;
    private TextField tf_newWeight;
    private JFXButton btn_goHome;
    private JFXButton btn_save;
    private DatePicker datePicker;
    private ToggleGroup toggleGroup;
    private ComboBox<String> unitComboBox;
    private RadioButton rb_option1;
    private RadioButton rb_option2;
    private RadioButton rb_option3;
    private UserController userController;

    /**
     * Initializes the UI components.
     *
     * @author Louis Brown
     */
    @Override
    public void initializeUI() {
        setComponents();
        setEvents();
        setScene();
        this.userController = UserController.getInstance();
    }


    /**
     * Sets up UI components.
     *
     * @author Louis Brown
     */
    @Override
    public void setComponents() {
        setLabels();
        setTextfields();
        setButtons();
        setRadioButton();
        setComboBox();
        setDatePicker();
    }

    /**
     * Sets event handlers for buttons.
     *
     * @author Louis Brown, Kenan Al Tal
     */
    @Override
    public void setEvents() {

        btn_goHome.setOnAction(event -> {
            goBack();
        });

        btn_save.setOnAction(event -> {
            ProfileController profileController = new ProfileController();
            String newWeightText = tf_newWeight.getText();
            double weight = 0;
            newWeightText = newWeightText.replace(",", ".");
            if (!newWeightText.isEmpty()) {
                if (newWeightText.matches("\\d*\\.?\\d+")) {
                    weight = Double.parseDouble(newWeightText);
                    if (weight == 0) {
                        showAlert("Error", "You cannot weigh 0 kg.", Alert.AlertType.ERROR);
                        return;
                    }

                    weight = convertToKg(weight,unitComboBox.getValue());

                } else {
                    showAlert("Error", "Invalid input! Please try again.", Alert.AlertType.ERROR);
                    return;
                }
            } else {
                weight = 0;
            }

            LocalDate dateOfBirth = datePicker.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateOfBirthText = "";
            String newHabit = habitValue();

            if (dateOfBirth != null) {
                if (!checkAge(dateOfBirth)) {
                    showAlert("Error", "You have to be at least 15 years of age to use this " +
                            "application.", Alert.AlertType.ERROR);
                    return;
                } else {
                    dateOfBirthText = dateOfBirth.format((formatter));
                }
            } else {
                dateOfBirthText = null;
            }

            boolean userUpdated = profileController.updateUser(userController.getId(), newHabit, weight,
                    dateOfBirthText);

            if (userUpdated) {
                showAlert("Success", "User information updated successfully!",
                        Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to update user information.", Alert.AlertType.ERROR);
            }
        });
    }

    /**
     * Sets up the scene layout.
     *
     * @author Louis Brown, Kenan Al Tal
     */
    @Override
    public void setScene() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(lbl_newWeight, 0, 1);

        HBox unitBox = new HBox(20, tf_newWeight, unitComboBox);
        gridPane.add(unitBox,0,2);
        GridPane.setHalignment(unitBox, HPos.CENTER);

        gridPane.add(lbl_newDateOfBirth, 0, 4);
        gridPane.add(datePicker, 0, 5);
        gridPane.add(lbl_newHabit, 0, 6);
        gridPane.add(rb_option1, 0, 7);
        gridPane.add(rb_option2, 0, 8);
        gridPane.add(rb_option3, 0, 9);

        VBox mainContent = new VBox();
        mainContent.setAlignment(Pos.TOP_CENTER);

        Image logoImageGreen = new Image (getClass().getResourceAsStream("/Koffeinkoll_green_green.png"));
        ImageView logoImageViewGreen = new ImageView(logoImageGreen);
        logoImageViewGreen.setFitHeight(220);
        logoImageViewGreen.setFitWidth(220);

        Label titleLabel = new Label("Edit Profile");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        Color textColor = Color.rgb(0,60,0);
        titleLabel.setTextFill(textColor);
        titleLabel.setTranslateY(50);

        mainContent.getChildren().addAll(logoImageViewGreen,titleLabel);

        borderPane.setPadding(new Insets(20));
        borderPane.setTop(mainContent);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(mainContent, Pos.CENTER);

        HBox buttonBox = new HBox(20, btn_goHome, btn_save);
        buttonBox.setAlignment(Pos.CENTER);

        borderPane.setBottom(buttonBox);
    }

    /**
     * Sets up labels.
     *
     * @author Kenan Al Tal
     */
    private void setLabels() {
        lbl_newHabit = setLabelStyle("Approximately how many drinks containing \ncaffeine do you consume in a day?");
        lbl_newWeight = setLabelStyle("New Weight:");
        lbl_newDateOfBirth = setLabelStyle("New Date of Birth:");
    }

    /**
     * Sets up text fields.
     *
     * @author Kenan Al Tal
     */
    private void setTextfields() {
        tf_newWeight = setTextField();
        tf_newWeight.setPromptText("Enter new weight");
    }

    /**
     * Sets up buttons.
     *
     * @author Kenan Al Tal
     */
    private void setButtons() {
        btn_goHome = new JFXButton("Home");
        btn_goHome.setStyle(setButtonStyle());

        btn_save = new JFXButton("Save");
        btn_save.setStyle(setButtonStyle());
    }

    /**
     * Sets up radio buttons.
     *
     * @author Louis Brown
     */
    private void setRadioButton() {
        toggleGroup = new ToggleGroup();

        rb_option1 = new RadioButton("0-1");
        rb_option1.setToggleGroup(toggleGroup);

        rb_option2 = new RadioButton("1-2");
        rb_option2.setToggleGroup(toggleGroup);

        rb_option3 = new RadioButton("2-5");
        rb_option3.setToggleGroup(toggleGroup);
    }

    private void setComboBox() {
        unitComboBox = new ComboBox<>();
        unitComboBox.getItems().addAll("kg", "lbs");
        unitComboBox.setValue("kg");
    }

    /**
     * Retrieves the selected habit value from radio buttons.
     *
     * @return The selected habit value.
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
     * Sets up date picker.
     *
     * @author Alanah Coleman
     */
    private void setDatePicker() {
        datePicker = new DatePicker();
        datePicker.setPromptText("YYYY-MM-DD");
    }

    /**
     * Checks if the chosen date is at least fifteen years ago.
     *
     * @param chosenDate The chosen date.
     * @return True if the date is at least fifteen years ago, false otherwise.
     * @author Louis Brown
     */
    private boolean checkAge(LocalDate chosenDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate fifteenYearsAgo = currentDate.minusYears(15);
        return chosenDate.isBefore(fifteenYearsAgo) || chosenDate.isEqual(fifteenYearsAgo);
    }

    private double convertToKg(double weight, String selectedUnit) {
        switch (selectedUnit) {
            case "kg":
                return weight;
            case "lbs":
                return weight * 0.454592;
            default:
                return 0.0;
        }
    }

    /**
     * Returns to the home page.
     *
     * @author Kenan Al Tal
     */
    private void goBack() {
        changePage(new HomePage());
    }
}