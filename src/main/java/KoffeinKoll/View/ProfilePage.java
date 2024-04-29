package KoffeinKoll.View;

import KoffeinKoll.Controller.ProfileController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.DatePicker;


public class ProfilePage extends A_Page {
    private Label lbl_newHabit;
    private Label lbl_newWeight;
    private Label lbl_newDateOfBirth;
    private TextField tf_newHabit;
    private TextField tf_newWeight;
    private TextField tf_newDateOfBirth;
    private JFXButton btn_goHome;
    private JFXButton btn_save;
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
    }

    @Override
    public void setEvents() {
        btn_goHome.setOnAction(event -> {
            goBack();
        });

        btn_save.setOnAction(event -> {
            ProfileController profileController = new ProfileController();
            String newHabit = tf_newHabit.getText();
            String newWeightText = tf_newWeight.getText();
            String newDateOfBirth = tf_newDateOfBirth.getText();

            // Validate input and save new user information
            boolean userUpdated = profileController.updateUser(2, newHabit, newWeightText, newDateOfBirth);

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
    }

    @Override
    public void setScene() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(lbl_newHabit, 0, 0);
        gridPane.add(tf_newHabit, 0, 1);
        gridPane.add(lbl_newWeight, 0, 2);
        gridPane.add(tf_newWeight, 0, 3);
        gridPane.add(lbl_newDateOfBirth, 0, 4);
       // gridPane.add(tf_newDateOfBirth, 0, 5);
        gridPane.add(datePicker, 0, 5);

        borderPane.setPadding(new Insets(20));
        borderPane.setTop(lbl_title);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(lbl_title, Pos.CENTER);

        HBox buttonBox = new HBox(20, btn_goHome, btn_save);
        buttonBox.setAlignment(Pos.CENTER);

        // Placing the button box at the bottom
        borderPane.setBottom(buttonBox);
    }

    private void setLabels() {
        lbl_newHabit = setLabelStyle("New Height:");
        lbl_newWeight = setLabelStyle("New Weight:");
        lbl_newDateOfBirth = setLabelStyle("New Date of Birth:");
    }

    private void setTextfields() {
        tf_newHabit = setTextField();
        tf_newHabit.setPromptText("Height (cm)");

        tf_newWeight = setTextField();
        tf_newWeight.setPromptText("Weight (kg)");

       // tf_newDateOfBirth = setTextField();
       // tf_newDateOfBirth.setPromptText("YYYY-MM-DD");

        datePicker = new DatePicker();
        datePicker.setPromptText("Select Date of Birth");

    }

    private void setButtons() {
        btn_goHome = new JFXButton("Back");
        btn_goHome.setStyle(setButtonStyle());

        btn_save = new JFXButton("Save");
        btn_save.setStyle(setButtonStyle());
    }

    private void goBack() {
        changePage(new HomePage());
    }
}
