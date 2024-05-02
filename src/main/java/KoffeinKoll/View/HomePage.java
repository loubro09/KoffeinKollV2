package KoffeinKoll.View;

import KoffeinKoll.Controller.LoginController;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.geometry.Insets;

public class HomePage extends A_Page {

    private JFXButton btn_statistics;
    private JFXButton btn_info;
    private JFXButton btn_profile;
    private JFXButton btn_logOut;
    private JFXButton btn_logBeverage;
    private CustomGauge customGauge;
    private PercentageGauge percentageGauge;


    private String userName;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String username) {
         userName = username;
    }


    @Override
    public void initializeUI() {
        setComponents();
        setEvents();
        setScene();
    }

    @Override
    public void setComponents() {
        setButtons();

    }

    @Override
    public void setScene() {
        VBox mainContent = new VBox();
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setSpacing(20);

        // Adding the custom gauge to the main content
        customGauge = new CustomGauge();
        mainContent.getChildren().addAll(lbl_title, customGauge);


        percentageGauge = new PercentageGauge(userName);


        mainContent.getChildren().add(percentageGauge);

        // Creating an HBox for the buttons
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        buttonBox.getChildren().addAll(btn_profile, btn_statistics, btn_info, btn_logOut);

        // Creating a VBox for the Log Beverage button and the button box
        VBox buttonVBox = new VBox();
        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.setSpacing(20);
        buttonVBox.getChildren().addAll(btn_logBeverage, buttonBox);

        // Setting margins for the main content and the button VBox
        BorderPane.setMargin(mainContent, new Insets(50));
        BorderPane.setMargin(buttonVBox, new Insets(20));

        // Placing the main content in the center and the button VBox at the bottom
        borderPane.setCenter(mainContent);
        borderPane.setBottom(buttonVBox);

    }

    @Override
    public void setEvents() {
        // Close current main window
        btn_logOut.setOnAction(e -> {stage.close();});

        for (JFXButton button : new JFXButton[]{btn_info}) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    changePage(new InfoPage());
                }
            });
        }

        for (JFXButton button : new JFXButton[]{btn_logBeverage}) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    changePage(new BeverageMenuPage());
                }
            });
        }

        for (JFXButton button : new JFXButton[]{btn_profile}) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    changePage(new ProfilePage());
                }
            });
        }

        for (JFXButton button : new JFXButton[]{btn_statistics}) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    changePage(new StatisticsPage());
                }
            });
        }
    }

    private void setButtons() {
        btn_statistics = new JFXButton("Statistics");
        btn_info = new JFXButton("Info");
        btn_profile = new JFXButton("Profile");
        btn_logOut = new JFXButton("Log Out");
        btn_logBeverage = new JFXButton("Log Beverage");

        btn_statistics.setStyle(setButtonStyle());
        btn_info.setStyle(setButtonStyle());
        btn_profile.setStyle(setButtonStyle());
        btn_logOut.setStyle(setButtonStyle());
        btn_logBeverage.setStyle(beverageButtonStyle());

        setIcon(FontAwesomeIcon.AREA_CHART, btn_statistics);
        setIcon(FontAwesomeIcon.INFO_CIRCLE, btn_info);
        setIcon(FontAwesomeIcon.USER, btn_profile);
        setIcon(FontAwesomeIcon.SIGN_OUT, btn_logOut);
        setIcon(FontAwesomeIcon.COFFEE, btn_logBeverage);
    }

    private void setIcon(FontAwesomeIcon iconType, JFXButton button) {
        Text temp = GlyphsDude.createIcon(iconType, "2em");
        temp.setFill(Color.WHITE);
        button.setGraphic(temp);
    }

    private String beverageButtonStyle() {
        String style = "-fx-background-color:\n" +
                "            #090a0c,\n" +
                "            linear-gradient(#8fbc8f 0%, #8fbc8f 20%, #8fbc8f 100%),\n" +
                "            linear-gradient(#8fbc8f, #8fbc8f),\n" +
                "            radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(black, black);\n" +
                "    -fx-font-size: 30px;\n" +
                "    -fx-padding: 10 20 10 20;";
        return style;
    }
}
