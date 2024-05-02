package KoffeinKoll.View;

import KoffeinKoll.Controller.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

public abstract class A_Page implements I_Page {
    protected Stage stage;
    protected BorderPane borderPane;
    protected Scene scene;
    protected Label lbl_title;

    public void initialPage(Stage stage) {
        setStage(stage);
        setStageSize();
        setWindowTitle();
        createBordePane();
        createScene();
        setBackground();
        setTitle();
        initializeUI();
    }

    public BorderPane getBorderPane() { return borderPane; }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setWindowTitle() {
        stage.setTitle("KoffeinKoll - Caffeine Management Tool");
    }

    @Override
    public void setStageSize() {
        stage.setWidth(800);
        stage.setHeight(800);
    }

    @Override
    public void createScene() {
        scene = new Scene(borderPane);
    }

    @Override
    public void createBordePane() {
        this.borderPane = new BorderPane();
    }

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

    @Override
    public void setTitle() {
        lbl_title = new Label("KoffeinKoll");
        lbl_title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 46));
        lbl_title.setTextFill(Color.rgb(0, 70, 0));
    }

    @Override
    public void setBackground() {
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        stage.setScene(scene);
        stage.show();
    }

    protected TextField setTextField(){
        TextField fieldStyle = new TextField();
        fieldStyle.setFont(Font.font("Arial", 14));
        fieldStyle.setPrefWidth(220);
        fieldStyle.setPrefHeight(30);
        fieldStyle.setStyle(" -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        return fieldStyle;
    }

    protected PasswordField setPasswordField(){
        PasswordField fieldStyle = new PasswordField();
        fieldStyle.setFont(Font.font("Arial", 14));
        fieldStyle.setPrefWidth(220);
        fieldStyle.setPrefHeight(30);
        fieldStyle.setStyle(" -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        //"-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-padding: 5px;"
        return fieldStyle;
    }

    protected Label setLabelStyle(String text) {
        Label lbl_labelStyle = new Label(text);
        lbl_labelStyle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Color labelColor = Color.rgb(0, 60, 0);
        lbl_labelStyle.setTextFill(labelColor);
        return lbl_labelStyle;
    }

    public void changePage(A_Page newPage) {
        newPage.initialPage(stage);
        stage.setScene(newPage.scene); // Set the scene to the login page's scene
        stage.show();
    }
}
