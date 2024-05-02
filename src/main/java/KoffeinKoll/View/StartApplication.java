package KoffeinKoll.View;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main entry point for the KoffeinKoll application.
 * @author Louis Brown
 */
public class StartApplication extends Application {
    private Stage stage;

    /**
     * The main entry point for JavaFX applications.
     * @param stage The primary stage for this application, onto which the application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        showLoginPage();
    }

    /**
     * Displays the login page of the application.
     */
    public void showLoginPage() {
        LogInPage loginPage = new LogInPage();
        loginPage.initialPage(stage); // Initialize the page directly
        stage.setScene(loginPage.scene); // Set the scene to the login page's scene
        stage.show();
    }

    /**
     * The main method, launching the JavaFX application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}