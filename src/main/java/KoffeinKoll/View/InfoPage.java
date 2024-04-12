package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class InfoPage extends Application {

    private Stage infoStage;

    public void start(Stage infoStage){
        this.infoStage = infoStage;

        infoStage.setTitle("KoffeinKoll - Caffeine Management Tool");
        infoStage.setWidth(800);
        infoStage.setHeight(800);
        //Huvudtitel
        Label titleLabel = new Label("KoffeinKoll");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 46));
        // Definierar den färg som all rubrikstext bör ha
        Color labelColor = Color.rgb(0, 60, 0);
        titleLabel.setTextFill(labelColor);


        JFXButton goBack = koffeinKollButtons("Go Back");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(gridPane);

        borderPane.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        // Creating a HBox for buttons
        HBox buttonHBox = new HBox(20);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(goBack);
        borderPane.setBottom(buttonHBox);

        Scene scene = new Scene(borderPane, 800, 800);

        // Setting background color as a gradient centered with yellow in the middle
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        infoStage.setScene(scene);
        infoStage.show();
        goBack.setOnAction(e -> {
            goBack();
        });

    }
    private JFXButton koffeinKollButtons(String text){
        String styleButtons = "-fx-background-color:\n" +
                "            #090a0c,\n" +
                "            linear-gradient(#0a4a1d 0%, #8fbc8f 20%, #8fbc8f 50%, #c0dbad 100%),\n" +
                "            linear-gradient(#c0dbad, #8fbc8f),\n" +
                "            radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(black, darkgreen);\n" +
                "    -fx-font-size: 20px;\n" +
                "    -fx-padding: 10 20 10 20;"+
                "    -fx-font-weight: bold";

        JFXButton button = new JFXButton(text);
        button.setStyle(styleButtons);
        return button;
    }
    private void goBack() {
        HomePage homePage = new HomePage();
        homePage.start(infoStage);
    }
    public static void main(String[] args) {
        launch(args);
    }

}
