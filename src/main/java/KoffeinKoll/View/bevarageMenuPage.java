package KoffeinKoll.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

public class bevarageMenuPage extends Application{
        private Stage menuStage;
        @Override
        public void start(Stage menuStage) {
            this.menuStage = menuStage;

            menuStage.setTitle("KoffeinKoll - Caffeine Management Tool");
            menuStage.setWidth(800);
            menuStage.setHeight(800);

            //Huvudtitel
            Label titleLabel = new Label("KoffeinKoll");
            titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 46));

            Button coffeButton = new Button("Regular Coffee");
            Button espresso1Button = new Button("Single Shot Espresso");
            Button espresso2Button = new Button("Double shot Espresso");
            Button teaButton = new Button("Tea");
            Button mateButton = new Button("Mate");
            Button energyDrinkButton = new Button("Energy drink");
            Button sodaButton = new Button("Soda");


            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setVgap(10);
            gridPane.add(coffeButton,0,0);
            gridPane.add(espresso1Button, 0,1);
            gridPane.add(espresso2Button,0,2);
            gridPane.add(teaButton, 0,3);
            gridPane.add(mateButton,0,4);
            gridPane.add(energyDrinkButton,0,5);
            gridPane.add(sodaButton,0,6);

            BorderPane borderPane = new BorderPane();
            borderPane.setPadding(new Insets(20));
            borderPane.setTop(titleLabel);
            borderPane.setCenter(gridPane);

            // Creating a VBox for main page
            HBox topHBox = new HBox();
            topHBox.getChildren().add(titleLabel);
            topHBox.setAlignment(Pos.CENTER);
            borderPane.setTop(topHBox);

            Scene scene = new Scene(borderPane, 800, 800);

            // Setting background color as a gradient centered with yellow in the middle
            Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

            // Setting the Scene to the Stage
            menuStage.setScene(scene);
            menuStage.show();
        }

        //konstanter och skuggorna till TextFields
        private TextField textField(){
            TextField fieldStyle = new TextField();
            fieldStyle.setFont(Font.font("Arial", 14));
            fieldStyle.setPrefWidth(220);
            fieldStyle.setPrefHeight(30);
            fieldStyle.setStyle(" -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
            return fieldStyle;
        }
        public static void main(String[] args) {
            launch(args);
        }
    }


