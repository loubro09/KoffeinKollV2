package KoffeinKoll.View;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartApplication extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        showLoginPage();
    }

    public void showLoginPage() {
        TestLoginPage loginPage = new TestLoginPage();
        loginPage.initialPage(stage); // Initialize the page directly
        stage.setScene(loginPage.scene); // Set the scene to the login page's scene
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}