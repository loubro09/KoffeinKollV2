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

/**
 * InfoPage class represents a page displaying information about the application.
 * It extends A_Page and contains labels and a button.
 */
public class InfoPage extends A_Page{
    private Label lbl_info;
    private Label lbl_subTitle;
    private JFXButton btn_goHome;

    /**
     * Initializes the user interface components.
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
     * @author Louis Brown
     */
    @Override
    public void setComponents() {
        setLabels();
        setButtons();
    }

    /**
     * Sets up event handling.
     * @author                                                                                          //AUTHOR
     */
    @Override
    public void setEvents() {
        btn_goHome.setOnAction(e -> {goBack();});
    }

    /**
     * Sets up the scene layout.
     * @author                                                                                          //AUTHOR
     */
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

    /**
     * Sets up the labels.
     * @author                                                                                          //AUTHOR
     */
    private void setLabels() {
        lbl_subTitle = setLabelStyle("About");
        lbl_subTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));

        String infoText = "Welcome to KoffeinKoll, Your Caffeine Companion!\n" +
                "\n" +
                "KoffeinKoll is a desktop application designed to help you monitor your caffeine intake. Personalize your profile, log your consumption, and gain insights into your habits.\n" + "\n" + "Whether you're optimizing lifestyle choices or aiming to reduce caffeine intake, KoffeinKoll is here for you. Our app calculates your recommended daily intake, tracks consumption, and predicts caffeine processing time. Join us in enhancing health and wellness, one sip at a time.\n" +
                "\n" + "Created 2024\n" + "Alanah Coleman, Elias Olsson, Kenan Al Tal, Louis Brown and Ida Nordenswan";

        lbl_info = setLabelStyle(infoText);
        lbl_info.setWrapText(true);
        lbl_info.setPadding(new Insets(20, 100, 20, 100));
        lbl_info.setFont(Font.font("Arial", 16));
    }

    /**
     * Sets up the buttons.
     * @author                                                                                          //AUTHOR
     */
    private void setButtons() {
        btn_goHome = new JFXButton("Go Back");
        btn_goHome.setStyle(setButtonStyle());
    }

    /**
     * Navigates back to the home page.
     * @author                                                                                          //AUTHOR
     */
    private void goBack() {
        changePage(new HomePage());
    }
}
