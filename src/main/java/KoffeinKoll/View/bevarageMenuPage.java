package KoffeinKoll.View;

import javafx.application.Application;
import javafx.geometry.HPos;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
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

            Label littleTitleLabel = new Label("  Let's get started!\nChoose your drink:");
            littleTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 23));
            // Definierar den färg som all rubrikstext bör ha
            Color labelColor = Color.rgb(0, 60, 0);
            titleLabel.setTextFill(labelColor);
            littleTitleLabel.setTextFill(labelColor);


            Button coffeButton = koffeinKollButtons("Regular Coffee");
            Button espresso1Button = koffeinKollButtons("Single Shot Espresso");
            Button espresso2Button =  koffeinKollButtons("Double Shot Espresso");
            Button teaButton = koffeinKollButtons("Tea");
            Button mateButton = koffeinKollButtons("Mate");
            Button energyDrinkButton = koffeinKollButtons("Energy drink");
            Button sodaButton = koffeinKollButtons("Soda");

            for(Button button: new Button[]{
                    coffeButton,espresso1Button,espresso2Button,teaButton,mateButton,energyDrinkButton,sodaButton}){
                button.setMaxWidth(Double.MAX_VALUE);
                GridPane.setHalignment(button, HPos.CENTER);
            }

            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setVgap(10);
            gridPane.add(littleTitleLabel, 0,0);
            GridPane.setHalignment(littleTitleLabel,HPos.CENTER);
            gridPane.add(coffeButton,0,1);
            gridPane.add(espresso1Button, 0,2);
            gridPane.add(espresso2Button,0,3);
            gridPane.add(teaButton, 0,4);
            gridPane.add(mateButton,0,5);
            gridPane.add(energyDrinkButton,0,6);
            gridPane.add(sodaButton,0,7);

            BorderPane borderPane = new BorderPane();
            borderPane.setPadding(new Insets(20));

            borderPane.setTop(titleLabel);
            BorderPane.setAlignment(titleLabel, Pos.CENTER);
            borderPane.setCenter(gridPane);
            BorderPane.setAlignment(gridPane, Pos.CENTER);

            Scene scene = new Scene(borderPane,800,800);


            // Setting background color as a gradient centered with yellow in the middle
            Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

            // Setting the Scene to the Stage
            menuStage.setScene(scene);
            menuStage.show();
        }

        //konstant för hur knapparna ska se ut
        private Button koffeinKollButtons(String text){
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

            Button button = new Button(text);
            button.setStyle(styleButtons);
            return button;

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


