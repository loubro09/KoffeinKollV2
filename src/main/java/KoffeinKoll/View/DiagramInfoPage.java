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

public class DiagramInfoPage extends A_Page{
    private Label lbl_subTitle;
    private Label lbl_info;
    private JFXButton btn_goBack;

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
        btn_goBack.setOnAction(e -> {goBack();});
    }

    @Override
    public void setScene() {
        VBox titleBox = new VBox(60);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(lbl_title, lbl_subTitle);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        HBox buttonHBox = new HBox(0);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(btn_goBack);
        borderPane.setBottom(buttonHBox);

        borderPane.setPadding(new Insets(30));
        borderPane.setCenter(gridPane);

        borderPane.setTop(titleBox);
        BorderPane.setAlignment(titleBox, Pos.CENTER);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        borderPane.setCenter(lbl_info);
    }

    private void setLabels() {
        lbl_subTitle = setLabelStyle("About");
        lbl_subTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));

        String infoText = "Welcome to KoffeinKoll, Your Caffeine Companion!";
        lbl_info = setLabelStyle(infoText);
        lbl_info.setWrapText(true);
        lbl_info.setPadding(new Insets(20, 100, 20, 100));
        lbl_info.setFont(Font.font("Arial", 16));
    }

    private void setButtons() {
        btn_goBack = new JFXButton("Go Back");
        btn_goBack.setStyle(setButtonStyle());
    }

    private void goBack() {
        changePage(new StatisticsPage());
    }
}
