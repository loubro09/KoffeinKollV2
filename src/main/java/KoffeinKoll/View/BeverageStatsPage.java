package KoffeinKoll.View;

import KoffeinKoll.Controller.AlgorithmController;
import KoffeinKoll.Controller.BeverageController;
import KoffeinKoll.Controller.UserController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * BeverageStatsPage class represents the page for logging beverage consumption.
 * It extends A_Page and implements methods to initialize UI components, set event handlers, and set the scene.
 */
public class BeverageStatsPage extends A_Page {
    private TextField tf_amountCL;
    private DatePicker datePicker;
    private TextField timeTextField;
    private Label lbl_beverageTitle;
    private JFXButton btn_goBack;
    private JFXButton btn_goHome;
    private JFXButton btn_log;
    private JFXButton btn_useCurrentTime;
    private Label lbl_amountCL;
    private Label lbl_time;
    private BeverageController beverageController = new BeverageController();
    private int beverageID;

    /**
     * Constructs a new instance of BeverageStatsPage with the specified beverage ID.
     * @param beverageID The ID of the beverage.
     * @author                                                                                          //AUTHOR
     */
    public BeverageStatsPage(int beverageID) {
        this.beverageID = beverageID;
    }

    /**
     * Initializes the UI components.
     * @author Louis Brown
     */
    @Override
    public void initializeUI() {
        setComponents();
        setEvents();
        setScene();
    }

    /**
     * Sets up UI components.
     * @author Louis Brown
     */
    @Override
    public void setComponents() {
        setLabels();
        setTextfields();
        setButtons();
    }

    /**
     * Sets event handlers for buttons.
     * @author                                                                                          //AUTHOR
     */
    @Override
    public void setEvents() {
        btn_goBack.setOnAction(e -> goBack());
        btn_goHome.setOnAction(e -> goToHomePage());
        btn_log.setOnAction(e -> validateInputs());
        btn_useCurrentTime.setOnAction(e -> useCurrentTime());
    }

    /**
     * Sets up the scene layout.
     * @author                                                                                          //AUTHOR
     */
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
        gridPane.add(datePicker, 0, 5);
        gridPane.add(timeTextField, 0, 6);
        

        HBox currentTimeBox = new HBox(btn_useCurrentTime);
        currentTimeBox.setAlignment(Pos.CENTER);
        gridPane.add(currentTimeBox, 1, 7);

        gridPane.add(btn_log, 0, 8, 2, 1);
        GridPane.setHalignment(btn_log, HPos.CENTER);

        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(gridPane);
        borderPane.setTop(topBox);
        borderPane.setBottom(buttonBox);
    }

    /**
     * Sets up labels.
     * @author                                                                                          //AUTHOR
     */
    private void setLabels() {
        lbl_beverageTitle = setLabelStyle("Log Amount");
        lbl_beverageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 46));

        lbl_amountCL = setLabelStyle("Amount CL");
        lbl_time = setLabelStyle("Time");
    }

    /**
     * Sets up text fields.
     * @author                                                                                          //AUTHOR
     */
    private void setTextfields() {
        tf_amountCL = setTextField();
        tf_amountCL.setPromptText("Enter Amount in CL");

        datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");

        timeTextField = new TextField();
        timeTextField.setPromptText("Enter Time (HH:mm)");

    }

    /**
     * Sets up buttons.
     * @author                                                                                          //AUTHOR
     */
    private void setButtons() {
        btn_goBack = new JFXButton("Go Back");
        btn_goBack.setStyle(setButtonStyle());

        btn_goHome = new JFXButton("Home");
        btn_goHome.setStyle(setButtonStyle());

        btn_log = new JFXButton("Log Amount");
        btn_log.setStyle(setButtonStyle());

        btn_useCurrentTime = new JFXButton("Use Current Time");
        btn_useCurrentTime.setStyle(setButtonStyle());

    }

    /**
     * Validates user inputs for amount and date/time.
     * @author                                                                                          //AUTHOR
     */
    private void validateInputs() {
        if (!beverageController.validateAmount(tf_amountCL.getText())) {
            showAlert("Invalid Amount in CL", "Please enter a valid amount in CL.");
        } else if (!validateDateTime()) {
            showAlert("Invalid Time", "Please enter a valid date and time.");
        } else {
            processValidInputs();
        }
    }

    /**
     * Validates the date and time format.
     * @return True if the date and time are valid, otherwise false.
     * @author                                                                                          //AUTHOR
     */
    private boolean validateDateTime() {
        String time = timeTextField.getText();
        LocalDate date = datePicker.getValue();
        if (date == null) {
            return false;
        }
        if (time.isEmpty()) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime.parse(date + " " + time, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Processes valid user inputs.
     * @author                                                                                          //AUTHOR
     */
    private void processValidInputs() {
        LocalDate date = datePicker.getValue();
        String time = timeTextField.getText();
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
        Double amount = Double.valueOf(tf_amountCL.getText());

        UserController userController = UserController.getInstance();
        int userId = userController.getId();

        if (beverageController.insertUserHistory(userId, beverageID, LocalDate.from(dateTime), amount)) {
            showAlert("Success", "Consumption logged successfully!");
            AlgorithmController ac = new AlgorithmController(beverageID, amount);
        } else {
            showAlert("Database Error", "Failed to log consumption.");
        }
    }

    /**
     * Shows an alert dialog with the specified title and content.
     * @param title The title of the alert dialog.
     * @param content The content of the alert dialog.
     * @author                                                                                          //AUTHOR
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Navigates to the home page.
     * @author                                                                                          //AUTHOR
     */
    private void goToHomePage() {
        changePage(new HomePage());
    }

    /**
     * Navigates back to the beverage menu page.
     * @author                                                                                          //AUTHOR
     */
    private void goBack() {
        changePage(new BeverageMenuPage());
    }

    /**
     * Sets the current time in the time text field and date picker.
     * @author                                                                                          //AUTHOR
     */
    private void useCurrentTime() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        timeTextField.setText(currentTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        datePicker.setValue(currentDate);
    }
}
