package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import KoffeinKoll.Controller.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BeverageStats extends Application {
    private static final String BUTTON_STYLE = "-fx-background-color: #090a0c, linear-gradient(#0a4a1d 0%, #8fbc8f 20%, #8fbc8f 50%, #c0dbad 100%), linear-gradient(#c0dbad, #8fbc8f), radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0)); -fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0; -fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-font-family: \"Arial\"; -fx-text-fill: linear-gradient(black, darkgreen); -fx-font-size: 20px; -fx-padding: 10 20 10 20; -fx-font-weight: bold";
    private static final String LABEL_FONT = "Arial";
    private static final Color LABEL_COLOR = Color.rgb(0, 60, 0);
    private Stage stage;
    private BeverageController beverageController = new BeverageController();
    private TextField amountField, amountClField, timeField;
    private int beverageId;

    public BeverageStats(int beverageID) {
        this.beverageId = beverageID;
    }
    public BeverageStats() {

    }
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        setupStage();
        stage.setScene(createScene());
        stage.show();
    }

    private void setupStage() {
        stage.setTitle("KoffeinKoll - Caffeine Management Tool");
        stage.setWidth(800);
        stage.setHeight(800);
    }

    private Scene createScene() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));
        layout.setCenter(createGridPane());
        layout.setTop(createTopBox());
        layout.setBottom(createButtonBox());
        applyBackgroundGradient(layout);
        return new Scene(layout, 800, 800);
    }

    private void applyBackgroundGradient(BorderPane pane) {
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#c0dbad")),
                new Stop(1, Color.web("#fcf1cb"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        pane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));
    }

    private HBox createTopBox() {
        Label titleLabel = new Label("Log Amount");
        titleLabel.setFont(Font.font(LABEL_FONT, FontWeight.BOLD, 46));
        titleLabel.setTextFill(LABEL_COLOR);
        HBox topBox = new HBox(titleLabel);
        topBox.setAlignment(Pos.CENTER);
        return topBox;
    }

    private HBox createButtonBox() {
        JFXButton goBackButton = new JFXButton("Go Back");
        goBackButton.setStyle(BUTTON_STYLE);
        goBackButton.setOnAction(e -> goBack());
        JFXButton homeButton = new JFXButton("Home");
        homeButton.setStyle(BUTTON_STYLE);
        homeButton.setOnAction(e -> goToHomePage());
        HBox buttonBox = new HBox(20, goBackButton, homeButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        addGridContent(gridPane);
        return gridPane;
    }

    private void addGridContent(GridPane gridPane) {
        amountField = createTextField("Enter Amount");  // Assign to class-level field
        amountClField = createTextField("Enter Amount in CL");  // Assign to class-level field
        timeField = createTextField("Enter Time yyyy-MM-dd HH:mm");  // Assign to class-level field

        gridPane.add(createLabel("Number of Beverages"), 0, 0);
        gridPane.add(amountField, 1, 0);  // Use the field
        gridPane.add(createLabel("Amount CL"), 0, 1);
        gridPane.add(amountClField, 1, 1);  // Use the field
        gridPane.add(createLabel("Time"), 0, 2);
        gridPane.add(timeField, 1, 2);  // Use the field

        JFXButton logButton = new JFXButton("Log Amount");
        logButton.setStyle(BUTTON_STYLE);
        logButton.setOnAction(e -> validateInputs());
        gridPane.add(logButton, 0, 3, 2, 1);
        GridPane.setHalignment(logButton, HPos.CENTER);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font(LABEL_FONT, FontWeight.BOLD, 20));
        label.setTextFill(LABEL_COLOR);
        return label;
    }

    private TextField createTextField(String prompt) {
        TextField textField = new TextField();
        textField.setFont(Font.font(LABEL_FONT, 14));
        textField.setPrefWidth(220);
        textField.setPromptText(prompt);
        textField.setStyle("-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        return textField;
    }

    private void goToHomePage() {
        HomePage homePage = new HomePage();
        homePage.start(stage);
    }

    private void goBack() {
        BeverageMenuPage beverageMenuPage = new BeverageMenuPage();
        beverageMenuPage.start(stage);
    }
    private void validateInputs() {
        if (!beverageController.validateAmount(amountField.getText())) {
            showAlert("Invalid Amount", "Please enter a valid amount.");
        } else if (!beverageController.validateAmount(amountClField.getText())) {
            showAlert("Invalid Amount in CL", "Please enter a valid amount in CL.");
        } else if (!beverageController.validateDateTime(timeField.getText())) {
            showAlert("Invalid Time", "Time should be in (yyyy-MM-dd HH:mm) format.");
        } else {
            // If inputs are valid, proceed with further processing
            processValidInputs();
        }
    }

    private void processValidInputs() {
        int userId = 1; // Obtain the logged-in user's ID
        int beverageId = 1; // Determine the beverage ID, possibly from a selection
        LocalDate date = LocalDate.parse(timeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if (beverageController.insertUserHistory(userId, beverageId, date)) {
            showAlert("Success", "Consumption logged successfully!");
            System.out.println("Funka");
        } else {
            showAlert("Database Error", "Failed to log consumption.");
            System.out.println("Inte");
        }
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



