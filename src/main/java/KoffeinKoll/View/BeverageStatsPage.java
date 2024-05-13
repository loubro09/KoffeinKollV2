package KoffeinKoll.View;

import KoffeinKoll.Controller.AlgorithmController;
import KoffeinKoll.Controller.BeverageController;
import KoffeinKoll.Controller.CaffeineCalculator;
import KoffeinKoll.Controller.UserController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private TextField tf_time;
    private Label lbl_beverageTitle;
    private JFXButton btn_goBack;
    private JFXButton btn_goHome;
    private JFXButton btn_log;
    private JFXButton btn_useCurrentTime;
    private Label lbl_amountCL;
    private Label lbl_time;
    private BeverageController beverageController = new BeverageController();
    private int beverageID;
    private CaffeineCalculator caffeineCalculator = new CaffeineCalculator();
    private AlgorithmController algorithmController = new AlgorithmController();

    /**
     * Constructs a new instance of BeverageStatsPage with the specified beverage ID.
     *
     * @param beverageID The ID of the beverage.
     * @author Elias Olsson
     */
    public BeverageStatsPage(int beverageID) {
        this.beverageID = beverageID;
    }

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
    }

    /**
     * Sets event handlers for buttons.
     *
     * @author Elias Olsson
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
     *
     * @author Elias Olsson
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
        GridPane.setHalignment(lbl_amountCL, HPos.LEFT);
        gridPane.add(tf_amountCL, 0, 3);
        GridPane.setHalignment(tf_amountCL, HPos.CENTER); // Center align the TextField

        gridPane.add(lbl_time, 0, 4);
        GridPane.setHalignment(lbl_time, HPos.LEFT);
        gridPane.add(datePicker, 0, 5);
        GridPane.setHalignment(datePicker, HPos.LEFT); // Center align the DatePicker
        gridPane.add(tf_time, 0, 6);
        GridPane.setHalignment(tf_time, HPos.CENTER); // Center align the TextField

        HBox currentTimeBox = new HBox(btn_useCurrentTime);
        currentTimeBox.setAlignment(Pos.CENTER);
        gridPane.add(currentTimeBox, 0, 7);
        GridPane.setHalignment(currentTimeBox, HPos.CENTER); // Center align the HBox for the button

        gridPane.add(btn_log, 0, 8, 2, 1);
        GridPane.setHalignment(btn_log, HPos.CENTER); // Ensure button is centered across two columns

        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(gridPane);
        borderPane.setTop(topBox);
        borderPane.setBottom(buttonBox);
    }


    /**
     * Sets up labels.
     *
     * @author Elias Olsson
     */
    private void setLabels() {
        lbl_beverageTitle = setLabelStyle("Log Amount");
        lbl_beverageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 46));

        lbl_amountCL = setLabelStyle("Amount CL:");
        lbl_time = setLabelStyle("Time:");
    }

    /**
     * Sets up text fields.
     *
     * @author Elias Olsson
     */
    private void setTextfields() {
        tf_amountCL = setTextField();
        tf_amountCL.setPromptText("Enter Amount in CL");

        tf_amountCL.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_amountCL.setText(newValue.replaceAll("[^\\d]", ""));
            }
            warningOverconsumption();
        });


        datePicker = new DatePicker();
        datePicker.setPromptText("YYYY-MM-DD");

        tf_time = new TextField();
        tf_time.setPromptText("Enter Time (HH:mm)");

    }

    /**
     * Checks if the entered amount of beverage exceeds the recommended caffeine limit.
     * If the amount exceeds the limit, it displays a warning message.
     * @auhtor AlanahColeman
     */
    public void warningOverconsumption() {

        String enteredAmountText = tf_amountCL.getText().trim();
        if (!enteredAmountText.isEmpty()) {

            double enteredAmount = Double.parseDouble(enteredAmountText);
            double caffeineConcentration = algorithmController.getBeverageConcentration(beverageID);
            double recommendedLimit = caffeineCalculator.calculateExcessConsumption();

            double enteredCaffeine = enteredAmount * caffeineConcentration;


            if (enteredCaffeine > recommendedLimit) {
                int excess = (int) (enteredCaffeine - recommendedLimit);
                showAlert("Warning", "You have exceeded the recommended caffeine limit by " + excess + " mg", Alert.AlertType.WARNING);
            }
        }
    }

    /**
     * Sets up buttons.
     *
     * @author Elias Olsson
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
     *
     * @author Elias Olsson
     */
    private void validateInputs() {
        if (!beverageController.validateAmount(tf_amountCL.getText())) {
            showAlert("Invalid Amount in CL", "Please enter a valid amount in CL.", Alert.AlertType.ERROR);
        } else if (!validateDateTime()) {
            showAlert("Invalid Time", "Please enter a valid date and time.", Alert.AlertType.ERROR);
        } else {
            processValidInputs();
        }
    }

    /**
     * Validates the date and time format.
     *
     * @return True if the date and time are valid, otherwise false.
     * @author Elias Olsson
     */
    private boolean validateDateTime() {
        String time = tf_time.getText();
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
            e.printStackTrace();
            System.out.println("BeverageStatsPage : validateDateTime : Parsing date error.");
            return false;
        }
    }

    /**
     * Processes valid user inputs.
     *
     * @author Elias Olsson
     */
    private void processValidInputs() {
        LocalDate date = datePicker.getValue();
        String time = tf_time.getText();
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
        Double amount = Double.valueOf(tf_amountCL.getText());

        if (amount <= 0) {
            showAlert("Error", "You cannot enter an amount less than 1 cl.", Alert.AlertType.ERROR);
            return;
        }

        UserController userController = UserController.getInstance();
        int userId = userController.getId();

        if (beverageController.insertUserHistory(userId, beverageID, LocalDateTime.from(dateTime), amount)) {
            showAlert("Success", "Consumption logged successfully!", Alert.AlertType.INFORMATION);
            AlgorithmController ac = new AlgorithmController();
            ac.updateGaugeNewLog(beverageID, amount);

            goToHomePage();
        } else {
            showAlert("Database Error", "Failed to log consumption.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Navigates to the home page.
     *
     * @author Elias Olsson
     */
    private void goToHomePage() {
        changePage(new HomePage());
    }

    /**
     * Navigates back to the beverage menu page.
     *
     * @author Elias Olsson
     */
    private void goBack() {
        changePage(new BeverageMenuPage());
    }

    /**
     * Sets the current time in the time text field and date picker.
     *
     * @author Elias Olsson
     */
    private void useCurrentTime() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        tf_time.setText(currentTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        datePicker.setValue(currentDate);
    }
}
