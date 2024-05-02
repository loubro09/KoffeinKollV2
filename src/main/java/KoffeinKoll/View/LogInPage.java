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

public class LogInPage extends A_Page{

    private Label lbl_userName;
    private Label lbl_password;
    public TextField tf_userName;
    private PasswordField pf_password;
    private Hyperlink hl_registration;
    private JFXButton btn_logIn;


    public void initializeUI(){
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
        // Log in button action handler
        btn_logIn.setOnAction(event -> handleLogin());

        // Handle enter key press in password field
        pf_password.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                handleLogin();
                ev.consume();
            }
        });

        // Registration link action handler
        hl_registration.setOnAction(event -> handleReg());
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
        gridPane.add(hl_registration,0,5);
        gridPane.add(btn_logIn, 0, 7);
        gridPane.setHalignment(btn_logIn, Pos.CENTER.getHpos());


        borderPane = getBorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(gridPane);

        // Creating a VBox for main page
        HBox topHBox = new HBox();
        topHBox.getChildren().add(lbl_title);
        topHBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topHBox);
    }

    private void setLabels() {
        lbl_userName = setLabelStyle("Username");
        lbl_password = setLabelStyle("Password");
    }

    private void setTextfields() {
        tf_userName = setTextField();
        tf_userName.setPromptText("Enter username");
        pf_password = setPasswordField();
        pf_password.setPromptText("Enter password ");
    }

    private void setButtons() {
        btn_logIn = new JFXButton("Log in");
        btn_logIn.setStyle(setButtonStyle());

        hl_registration = new Hyperlink("Not registered? Create an account!");
        hl_registration.setFont(Font.font("Arial", 14));
    }

    private void handleLogin() {
        String username = tf_userName.getText();
        String password = pf_password.getText();

        // Logic for handling login here
        LoginController loginController = new LoginController();
        boolean loggedIn = loginController.logIn(username, password);

        if (loggedIn) {
            // If login successful, you might want to switch to another page
            //Assistant
            HomePage homePage = new HomePage();
            homePage.setUserName(username);
            changePage(homePage);
        } else {
            System.out.println("Login failed");
        }
    }

    private void handleReg() {
        changePage(new CreateUserPage());
    }

    public TextField getTf_userName() {
        return tf_userName;
    }
}
