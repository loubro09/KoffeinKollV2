package KoffeinKoll.View;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public abstract class A_Page implements I_Page{
    protected Stage stage;
    protected BorderPane borderPane;
    protected Scene scene;
    protected Label titleLabel;

    public void initialPage(Stage stage) {
        this.stage = stage;
        stage.setTitle("KoffeinKoll - Caffeine Management Tool");
        stage.setWidth(800);
        stage.setHeight(800);
        this.borderPane = new BorderPane();
        initializeUI();
        scene = new Scene(borderPane);
        createScene(borderPane);
        setBackground(scene);
    }

    public Stage getStage() { return stage; }
    public BorderPane getBorderPane() { return borderPane; }

    @Override
    public String setButtonStyle() {
        String styleButtons = "-fx-background-color:\n" +
                "            #090a0c,\n" +
                "            linear-gradient(#8fbc8f 0%, #8fbc8f 20%, #8fbc8f 100%),\n" +
                "            linear-gradient(#c0dbad, #8fbc8f),\n" +
                "            radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(black, black);\n" +
                "    -fx-font-size: 20px;\n" +
                "    -fx-padding: 10 20 10 20;";
        return styleButtons;
    }

    public void setTitle() {
        titleLabel = new Label("KoffeinKoll");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 46));
        titleLabel.setTextFill(Color.rgb(0, 70, 0));
    }

    @Override
    public void setBackground(Scene scene) {
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        stage.setScene(scene);
    }

    protected TextField setTextField(){
        TextField fieldStyle = new TextField();
        fieldStyle.setFont(Font.font("Arial", 14));
        fieldStyle.setPrefWidth(220);
        fieldStyle.setPrefHeight(30);
        fieldStyle.setStyle(" -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        return fieldStyle;
    }

    public void changePage(A_Page newPage) {
        newPage.initializeUI();
        borderPane.setCenter(newPage.getBorderPane());
    }
}
