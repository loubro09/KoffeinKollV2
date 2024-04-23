package KoffeinKoll.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public abstract class A_Page implements I_Page{
    protected Stage stage;
    protected BorderPane borderPane;
    protected Scene scene;

    public void initialPage(Stage stage) {
        this.stage = stage;
        this.borderPane = new BorderPane();
        scene = new Scene(borderPane);
        setUpStage();
    }

    public void setUpStage() {
        stage.setTitle("KoffeinKoll - Caffeine Management Tool");
        stage.setWidth(800);
        stage.setHeight(800);
        initializeUI();
        createScene(borderPane);
        setBackground(scene);
    }

    /*@Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        stage.setTitle("KoffeinKoll - Caffeine Management Tool");
        stage.setWidth(800);
        stage.setHeight(800);
        this.borderPane = new BorderPane();
        initializeUI();
        Scene scene = createScene(borderPane);
        setBackground(scene);
        stage.show();
    }*/

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
    public void setBackground(Scene scene) {
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        stage.setScene(scene);
    }

    public void changePage(A_Page newPage) {
        newPage.initializeUI();
        borderPane.setCenter(newPage.getBorderPane());
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
