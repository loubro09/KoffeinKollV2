package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import KoffeinKoll.View.BeverageStats;

public class BevarageMenuPage extends Application{
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
            //Undertitel
            Label littleTitleLabel = new Label("  Let's get started!\nChoose your drink:");
            littleTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 23));
            //Här definieras den färg som all rubrikstext bör ha
            Color labelColor = Color.rgb(0, 60, 0);
            titleLabel.setTextFill(labelColor);
            littleTitleLabel.setTextFill(labelColor);

            //Knappar till menyn med alla olika dryckor man kan välja (en åt gången)
            JFXButton coffeButton = koffeinKollButtons("Regular Coffee");
            JFXButton espresso1Button = koffeinKollButtons("Single Shot Espresso");
            JFXButton espresso2Button =  koffeinKollButtons("Double Shot Espresso");
            JFXButton teaButton = koffeinKollButtons("Tea");
            JFXButton mateButton = koffeinKollButtons("Mate");
            JFXButton energyDrinkButton = koffeinKollButtons("Energy drink");
            JFXButton sodaButton = koffeinKollButtons("Soda");
            JFXButton goBack = koffeinKollButtons("Go Back");

            //Alla knappar ska vara samma storlek och centrerade
            for(JFXButton button: new JFXButton[]{
                    coffeButton,espresso1Button,espresso2Button,teaButton,mateButton,energyDrinkButton,sodaButton}){
                button.setMaxWidth(Double.MAX_VALUE);
                GridPane.setHalignment(button, HPos.CENTER);
            }
            //Listner som väntar på anrop från användaren. När användaren trycker på en av knapparna i menyn stängs sidan och går vidare till nästa

            for(JFXButton button: new JFXButton[]{
                    coffeButton,espresso1Button,espresso2Button,teaButton,mateButton,energyDrinkButton,sodaButton}){
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        menuStage.close();
                        BeverageStats beverageStats = new BeverageStats();
                        Stage bevarageStats = new Stage();
                        beverageStats.start(bevarageStats);
                    }
                });
            }

            //Layout
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
            // Creating a HBox for buttons
            HBox buttonHBox = new HBox(20);
            buttonHBox.setAlignment(Pos.CENTER);
            buttonHBox.getChildren().addAll(goBack);
            borderPane.setBottom(buttonHBox);

            Scene scene = new Scene(borderPane,800,800);


            //Bakgrundsfärg
            Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

            // Scenen sätts till stagen så den visas
            menuStage.setScene(scene);
            menuStage.show();
            goBack.setOnAction(e -> {
                goBack();
            });
        }

        //konstant för hur knapparna ska se ut
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
        homePage.start(menuStage);
    }
        public static void main(String[] args) {
            launch(args);
        }
    }

