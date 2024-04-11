package KoffeinKoll.View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class HomePage extends Application{

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("KoffeinKoll - Caffeine Management Tool");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);

        // Creating labels
        Label titleLabel = new Label("KoffeinKoll");
        titleLabel.setFont(Font.font("Arial", 36));

        // Creating buttons
        Button accountButton = new Button("Account");
        Button statisticsButton = new Button("Statistics");
        Button logBeverageButton = new Button("Log Beverage");
        Button logoutButton = new Button("Logout");

        // Creating a BorderPane layout for main page
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        // Creating a HBox for buttons
        HBox buttonHBox = new HBox(20);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(accountButton, statisticsButton, logBeverageButton, logoutButton);
        borderPane.setBottom(buttonHBox);

        /*CustomGauge customGauge = new CustomGauge();
        //borderPane.setCenter(customGauge);
        BorderPane.setAlignment(customGauge, Pos.TOP_CENTER); // Center the gauge
        Insets gaugeMargins = new Insets(20, 200, 200, 200); // Top, Right, Bottom, Left
        BorderPane.setMargin(customGauge, gaugeMargins);
        borderPane.setCenter(customGauge);*/


        // Creating a VBox for main page
        HBox topHBox = new HBox();
        topHBox.getChildren().add(titleLabel);
        topHBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topHBox);

        // Creating a Scene and adding the BorderPane to it
        Scene scene = new Scene(borderPane, 800, 800);

        // Setting background color as a gradient centered with yellow in the middle
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        // Setting the Scene to the Stage
        primaryStage.setScene(scene);

        // Set action for logout button
        logoutButton.setOnAction(e -> {
            // Close current main window
            primaryStage.close();
            // Open login window
            //LoginWindow loginWindow = new LoginWindow();
            //loginWindow.start(new Stage());
        });
        for(Button button: new Button[]{logBeverageButton}){
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    primaryStage.close();
                    BevarageMenuPage bevarageMenuPage = new BevarageMenuPage();
                    Stage menuStage= new Stage();
                    bevarageMenuPage.start(menuStage);
                }
            });
        }

        // Displaying the Stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
