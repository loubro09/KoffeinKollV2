package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InfoPage extends A_Page{
    private Label lbl_info;
    private Label lbl_subTitle;
    private JFXButton btn_goHome;
    private boolean txt;

    public InfoPage(boolean txt) {
        this.txt = txt;
    }

    @Override
    public void initializeUI() {
        setComponents();
        setEvents();
        setScene();
    }

    @Override
    public void setComponents() {
        setLabels();
        setButtons();
    }

    @Override
    public void setEvents() {
        btn_goHome.setOnAction(e -> {goBack();});
    }

    @Override
    public void setScene() {
        VBox titleBox = new VBox(60);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(lbl_title, lbl_subTitle);

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

    private void setLabels() {
        lbl_subTitle = setLabelStyle("About");
        lbl_subTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        String infoText = null;
        if (txt) {
            infoText = "Welcome to KoffeinKoll, Your Caffeine Companion!\n\n" +
                    "KoffeinKoll is a desktop application designed to help you monitor your caffeine intake. Personalize your profile," +
                    " log your consumption, and gain insights into your habits.\n\n" +
                    "Whether you're optimizing lifestyle choices or aiming to reduce caffeine intake, KoffeinKoll is here for you. " +
                    "Our app calculates your recommended daily intake, tracks consumption, and predicts caffeine processing time. " +
                    "Join us in enhancing health and wellness, one sip at a time.\n\n" +
                    "**Disclaimer:** Please note that the methods used in KoffeinKoll are not scientifically proven. " +
                    "While we strive to provide accurate information and support healthy habits, individual responses " +
                    "to caffeine may vary. It's always best to consult with a healthcare professional for personalized advice.\n\n" +
                    "Created 2024\n" +
                    "Alanah Coleman, Elias Olsson, Kenan Al Tal, Louis Brown, and Ida Nordenswan";

        }
        else {
            lbl_subTitle = setLabelStyle("Diagrams");
            lbl_subTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));

            infoText = infoText =
                    "Caffeine Consumption Diagram:\n" +
                    "This diagram offers a glimpse into your caffeine consumption habits across different timeframes. It includes two types of charts:\n\n" +
                    "Caffeine Diagram:\n" +
                    "This chart displays your caffeine intake over the past week or month. It's a useful tool for understanding your caffeine consumption patterns, allowing you to make adjustments if needed. It also helps you track long-term trends and identify any changes in your consumption patterns over time.\n\n" +
                    "Beverages Diagram:\n" +
                    "This diagram categorizes and illustrates the types of drinks you've consumed. It gives you insights into the variety of beverages you consume, helping you make more informed choices about your beverage preferences.\n\n" +
                    "By exploring both diagrams, you can gain valuable insights into your caffeine intake habits, track your consumption trends, and make informed decisions to support a balanced and healthy lifestyle.";

        }

        lbl_info = setLabelStyle(infoText);
        lbl_info.setWrapText(true);
        lbl_info.setPadding(new Insets(20, 100, 20, 100));
        lbl_info.setFont(Font.font("Arial", 16));
    }

    private void setButtons() {
        btn_goHome = new JFXButton("Go Back");
        btn_goHome.setStyle(setButtonStyle());
    }

    private void goBack() {
        changePage(new StatisticsPage());
    }
}
