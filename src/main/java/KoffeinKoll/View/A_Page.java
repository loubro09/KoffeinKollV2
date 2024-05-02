package KoffeinKoll.View;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

/**
 * A_Page is an abstract class representing a generic page in the KoffeinKoll application's user interface.
 * It provides common functionality and styling for UI pages.
 * @author Louis Brown
 */
public abstract class A_Page implements I_Page {
    protected Stage stage;
    protected BorderPane borderPane;
    protected Scene scene;
    protected Label lbl_title;

    /**
     * Initializes the page with the provided stage.
     * @param stage The stage to associate with this page.
     */
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

    /**
     * Retrieves the BorderPane layout of this page.
     * @return The BorderPane layout.
     */
    public BorderPane getBorderPane() { return borderPane; }

    /**
     * Sets the stage for this page.
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the title of the stage.
     */
    @Override
    public void setWindowTitle() {
        stage.setTitle("KoffeinKoll - Caffeine Management Tool");
    }

    /**
     * Sets the size of the stage.
     */
    @Override
    public void setStageSize() {
        stage.setWidth(800);
        stage.setHeight(800);
    }

    /**
     * Creates the scene for this page.
     */
    @Override
    public void createScene() {
        scene = new Scene(borderPane);
    }

    /**
     * Creates the BorderPane layout for this page.
     */
    @Override
    public void createBordePane() {
        this.borderPane = new BorderPane();
    }

    /**
     * Defines the style for buttons on this page.
     * @return The CSS style for buttons.
     */
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

    /**
     * Sets the title label for this page.
     */
    @Override
    public void setTitle() {
        lbl_title = new Label("KoffeinKoll");
        lbl_title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 46));
        lbl_title.setTextFill(Color.rgb(0, 70, 0));
    }

    /**
     * Sets the background gradient for this page.
     */
    @Override
    public void setBackground() {
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#c0dbad")), new Stop(1, Color.web("#fcf1cb"))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        borderPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradient, null, null)));

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates and returns a styled TextField.
     * @return The styled TextField.
     */
    protected TextField setTextField(){
        TextField fieldStyle = new TextField();
        fieldStyle.setFont(Font.font("Arial", 14));
        fieldStyle.setPrefWidth(220);
        fieldStyle.setPrefHeight(30);
        fieldStyle.setStyle(" -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        return fieldStyle;
    }

    /**
     * Creates and returns a styled PasswordField.
     * @return The styled PasswordField.
     */
    protected PasswordField setPasswordField(){
        PasswordField fieldStyle = new PasswordField();
        fieldStyle.setFont(Font.font("Arial", 14));
        fieldStyle.setPrefWidth(220);
        fieldStyle.setPrefHeight(30);
        fieldStyle.setStyle(" -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        return fieldStyle;
    }

    /**
     * Creates and returns a styled Label with the specified text.
     * @param text The text for the label.
     * @return The styled Label.
     */
    protected Label setLabelStyle(String text) {
        Label lbl_labelStyle = new Label(text);
        lbl_labelStyle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Color labelColor = Color.rgb(0, 60, 0);
        lbl_labelStyle.setTextFill(labelColor);
        return lbl_labelStyle;
    }

    /**
     * Changes the current page to a new page.
     * @param newPage The new page to navigate to.
     */
    protected void changePage(A_Page newPage) {
        newPage.initialPage(stage);
        stage.setScene(newPage.scene); // Set the scene to the login page's scene
        stage.show();
    }

    /**
     * Shows an alert dialog with the specified title and content.
     * @param title The title of the alert dialog.
     * @param content The content of the alert dialog.
     * @param alertType The type of the alert
     * @author
     */
    protected void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}
