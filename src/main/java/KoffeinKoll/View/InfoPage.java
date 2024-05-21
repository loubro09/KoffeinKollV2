package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * InfoPage class represents a page displaying information about the application.
 * It extends A_Page and contains labels and a button.
 */
public class InfoPage extends A_Page {
    private Label lbl_info;
    private Label lbl_subTitle;
    private JFXButton btn_goHome;
    private boolean txt;

    public InfoPage(boolean txt) {
        this.txt = txt;
    }

    /**
     * Initializes the user interface components.
     *
     * @author Louis Brown
     */
    @Override
    public void initializeUI() {
        setComponents();
        setEvents();
        setScene();
    }

    /**
     * Sets up the UI components.
     *
     * @author Louis Brown
     */
    @Override
    public void setComponents() {
        setLabels();
        setButtons();
    }

    /**
     * Sets up event handling.
     *
     * @author Ida Nordenswan
     */
    @Override
    public void setEvents() {
        btn_goHome.setOnAction(e -> {
            goBack();
        });
    }

    /**
     * Sets up the scene layout.
     *
     * @author Ida Nordenswan
     */
    @Override
    public void setScene() {
        VBox titleBox = new VBox();
        titleBox.setAlignment(Pos.CENTER);

        Image logoImageGreen = new Image (getClass().getResourceAsStream("/Koffeinkoll_green_green.png"));
        ImageView logoImageViewGreen = new ImageView(logoImageGreen);
        logoImageViewGreen.setFitHeight(220);
        logoImageViewGreen.setFitWidth(220);
        logoImageViewGreen.setTranslateY(1);

        titleBox.getChildren().add(logoImageViewGreen);

        Label aboutTitleLabel = new Label("About");
        aboutTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        Color textColor = Color.rgb(0,70,0);
        aboutTitleLabel.setTextFill(textColor);
        aboutTitleLabel.setTranslateY(2);


        titleBox.getChildren().add(aboutTitleLabel);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        borderPane.setPadding(new Insets(30));
        borderPane.setCenter(gridPane);

        borderPane.setTop(titleBox);
        BorderPane.setAlignment(titleBox, Pos.CENTER);

        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        borderPane.setCenter(lbl_info);

        HBox buttonHBox = new HBox(0);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(btn_goHome);
        borderPane.setBottom(buttonHBox);
    }

    /**
     * Sets up the labels for info page.
     * Text is set depending on which info page is called.
     *
     * @author Kenan Al Tal, Ida Nordenswan
     */
    private void setLabels() {

        String infoText = null;
        if (txt) {
            infoText = "Welcome to KoffeinKoll, Your Caffeine Companion!\n\n" +
                    "KoffeinKoll is a desktop application designed to help you monitor your caffeine intake. " +
                    "Personalize your profile, log your consumption, and gain insights into your habits.\n\n" +
                    "Whether you're optimizing lifestyle choices or aiming to reduce caffeine intake, KoffeinKoll is " +
                    "here for you. Our app calculates your recommended daily intake, tracks consumption, and predicts" +
                    " caffeine processing time. Join us in enhancing health and wellness, one sip at a time.\n\n" +
                    "**Disclaimer:** Please note that the methods used in KoffeinKoll are not scientifically proven. " +
                    "While we strive to provide accurate information and support healthy habits, individual responses " +
                    "to caffeine may vary. It's always best to consult with a healthcare professional for " +
                    "personalized advice.\n\n Created 2024\n" +
                    "Alanah Coleman, Elias Olsson, Kenan Al Tal, Louis Brown, and Ida Nordenswan";

        } else {
            lbl_subTitle = setLabelStyle("Diagrams");
            lbl_subTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));

            infoText =
                    "Caffeine Consumption Diagram:\n" +
                            "This diagram offers a glimpse into your caffeine consumption habits across " +
                            "different timeframes. It includes two types of charts:\n\n" + "Caffeine Diagram:\n" + "This" +
                            " chart displays your caffeine intake over the past week or month. It's" +
                            " a useful tool for understanding your caffeine consumption patterns, allowing you to " +
                            "make adjustments if needed. It also helps you track long-term trends and identify any " +
                            "changes in your consumption patterns over time.\n\n" + "Beverages Diagram:\n" +
                            "This diagram categorizes and illustrates the types of drinks you've consumed. It gives " +
                            "you insights into the variety of beverages you consume, helping you make more informed " +
                            "choices about your beverage preferences.\n\n By exploring both diagrams, you can " +
                            "gain valuable insights into your caffeine intake habits, track your consumption " +
                            "trends, and make informed decisions to support a balanced and healthy lifestyle.";

        }
        lbl_info = setLabelStyle(infoText);
        lbl_info.setWrapText(true);
        lbl_info.setPadding(new Insets(0, 80, 0, 80));
        lbl_info.setFont(Font.font("Arial", 16));
        lbl_info.setTextAlignment(TextAlignment.CENTER);
    }

    /**
     * Sets up the buttons.
     *
     * @author Ida Nordenswan
     */
    private void setButtons() {
        if (!txt) {
            btn_goHome = new JFXButton("Go Back");
        }
        else {
            btn_goHome = new JFXButton("Home");
        }
        btn_goHome.setStyle(setButtonStyle());
    }

    /**
     * Navigates back to the home page.
     *
     * @author Kenan Al Tal
     */
    private void goBack() {

        if (!txt) {
            changePage(new StatisticsPage());
        } else {
            changePage(new HomePage());
        }
    }
}