package KoffeinKoll.View;

import KoffeinKoll.Controller.LoginController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


/**
 * LogInPage represents the user interface for logging into the KoffeinKoll application.
 * It allows users to enter their username and password, and provides functionality for logging in or
 * navigating to the registration page.
 */
public class LogInPage extends A_Page {

    private Label lbl_userName;
    private Label lbl_password;
    private TextField tf_userName;
    private PasswordField pf_password;
    private Hyperlink hl_registration;
    private JFXButton btn_logIn;

    /**
     * Initializes the UI components of the login page.
     *
     * @author Louis Brown
     */
    public void initializeUI() {
        setComponents();
        setEvents();
        setScene();
    }

    /**
     * Sets the UI components for the login page.
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
     * Sets the event handlers for UI controls on the login page.
     *
     * @author Louis Brown
     */
    @Override
    public void setEvents() {

        btn_logIn.setOnAction(event -> handleLogin());


        pf_password.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                handleLogin();
                ev.consume();
            }
        });


        hl_registration.setOnAction(event -> handleReg());
    }

    /**
     * Sets the scene layout for the login page.
     *
     * @author Louis Brown
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
        gridPane.add(hl_registration, 0, 5);
        gridPane.add(btn_logIn, 0, 7);
        gridPane.setHalignment(btn_logIn, Pos.CENTER.getHpos());

        Image logoImage = new Image(getClass().getResourceAsStream("/Koffeinkoll_green_gul.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(350); // Adjust the width as needed
        logoImageView.setFitHeight(350);

        VBox topVBox = new VBox();
        topVBox.setAlignment(Pos.CENTER);
        topVBox.getChildren().add(logoImageView);


        borderPane = getBorderPane();
        borderPane.setPadding(new Insets(70));
        borderPane.setTop(topVBox);
        borderPane.setCenter(gridPane);

    }

    /**
     * Sets labels for username and password fields.
     *
     * @author Louis Brown
     */
    private void setLabels() {
        lbl_userName = setLabelStyle("Username");
        lbl_password = setLabelStyle("Password");
    }

    /**
     * Sets text fields for username and password entry.
     *
     * @author Louis Brown
     */
    private void setTextfields() {
        tf_userName = setTextField();
        tf_userName.setPromptText("Enter username");
        pf_password = setPasswordField();
        pf_password.setPromptText("Enter password ");
    }

    /**
     * Sets buttons for login and registration.
     *
     * @author Louis Brown
     */
    private void setButtons() {
        btn_logIn = new JFXButton("Log in");
        btn_logIn.setStyle(setButtonStyle());

        hl_registration = new Hyperlink("Not registered? Create an account!");
        hl_registration.setFont(Font.font("Arial", 14));
    }

    /**
     * Handles the login process when the login button is clicked.
     *
     * @author Louis Brown
     */
    private void handleLogin() {
        String username = tf_userName.getText();
        String password = pf_password.getText();

        LoginController loginController = new LoginController();
        boolean loggedIn = loginController.logIn(username, password);

        if (loggedIn) {
            changePage(new HomePage());
        } else {
            showAlert("Login failed", "Login Failed", Alert.AlertType.ERROR);
        }
    }

    /**
     * Handles the navigation to the registration page when the registration link is clicked.
     *
     * @author Louis Brown
     */
    private void handleReg() {
        changePage(new CreateUserPage());
    }
}