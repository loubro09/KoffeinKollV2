package KoffeinKoll.View;

import KoffeinKoll.Controller.BeverageController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BeverageStatsPage extends A_Page{
    private TextField tf_amount;
    private TextField tf_amountCL;
    private TextField tf_time;
    private Label lbl_beverageTitle;
    private JFXButton btn_goBack;
    private JFXButton btn_goHome;
    private JFXButton btn_log;
    private Label lbl_amountCL;
    private Label lbl_time;
    private BeverageController beverageController = new BeverageController();
    private int beverageID;

    public BeverageStatsPage(int beverageID) {
        this.beverageID = beverageID;
    }

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
        btn_goBack.setOnAction(e -> goBack());
        btn_goHome.setOnAction(e -> goToHomePage());
        btn_log.setOnAction(e -> validateInputs());
    }

    @Override
    public void setScene() {
        HBox topBox = new HBox(lbl_beverageTitle);
        topBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(20, btn_goBack, btn_goHome);
        buttonBox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(lbl_amountCL, 0, 2);
        gridPane.add(tf_amountCL, 0, 3);

        gridPane.add(lbl_time, 0, 4);
        gridPane.add(tf_time, 0, 5);

        gridPane.add(btn_log, 0, 6, 2, 1);
        GridPane.setHalignment(btn_log, HPos.CENTER);

        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(gridPane);
        borderPane.setTop(topBox);
        borderPane.setBottom(buttonBox);
    }

    private void setLabels() {
        lbl_beverageTitle = setLabelStyle("Log Amount");
        lbl_beverageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 46));

        lbl_amountCL = setLabelStyle("Amount CL");
        lbl_time = setLabelStyle("Time");
    }

    private void setTextfields() {
        tf_amountCL = setTextField();
        tf_amountCL.setPromptText("Enter Amount in CL");
        tf_time = setTextField();
        tf_time.setPromptText("Enter Time yyyy-MM-dd HH:mm");
    }

    private void setButtons() {
        btn_goBack = new JFXButton("Go Back");
        btn_goBack.setStyle(setButtonStyle());

        btn_goHome = new JFXButton("Home");
        btn_goHome.setStyle(setButtonStyle());

        btn_log = new JFXButton("Log Amount");
        btn_log.setStyle(setButtonStyle());
    }

    private void validateInputs() {
        if (!beverageController.validateAmount(tf_amountCL.getText())) {
            showAlert("Invalid Amount in CL", "Please enter a valid amount in CL.");
        } else if (!beverageController.validateDateTime(tf_time.getText())) {
            showAlert("Invalid Time", "Time should be in (yyyy-MM-dd HH:mm) format.");
        } else {
            // If inputs are valid, proceed with further processing
            processValidInputs();
        }
    }

    private void processValidInputs() {
        int userId = 1; // Obtain the logged-in user's ID
        LocalDate date = LocalDate.parse(tf_time.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if (beverageController.insertUserHistory(userId, beverageID, date)) {
            showAlert("Success", "Consumption logged successfully!");
            System.out.println("Funka");
        } else {
            showAlert("Database Error", "Failed to log consumption.");
            System.out.println("Inte");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void goToHomePage() {
        changePage(new HomePage());
    }

    private void goBack() {
        changePage(new BeverageMenuPage());
    }
}
