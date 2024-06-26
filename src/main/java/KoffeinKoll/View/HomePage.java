package KoffeinKoll.View;

import KoffeinKoll.Controller.AlgorithmController;
import KoffeinKoll.Controller.UserController;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * HomePage represents the main page of the KoffeinKoll application where users can view their statistics, access information,
 * log out, and log their beverage consumption.
 * It displays a custom gauge to visualize caffeine intake and provides buttons for accessing different functionalities.
 */
public class HomePage extends A_Page {

    private JFXButton btn_statistics;
    private JFXButton btn_info;
    private JFXButton btn_profile;
    private JFXButton btn_logOut;
    private JFXButton btn_logBeverage;
    private CustomGauge customGauge;
    private AlgorithmController algorithmController;
    private PercentageGauge percentageGauge;

    /**
     * Initializes the UI components of the home page.
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
     * Sets the UI components for the home page.
     *
     * @author Louis Brown
     */
    @Override
    public void setComponents() {
        setButtons();
        algorithmController = new AlgorithmController();
    }

    /**
     * Sets the scene layout for the home page.
     *
     * @author Louis Brown
     */
    @Override
    public void setScene() {
        VBox mainContent = new VBox();
        mainContent.setAlignment(Pos.TOP_CENTER);

        Image logoImageGreen = new Image (getClass().getResourceAsStream("/Koffeinkoll_green_green.png"));
        ImageView logoImageViewGreen = new ImageView(logoImageGreen);
        logoImageViewGreen.setFitHeight(220);
        logoImageViewGreen.setFitWidth(220);

        double totalTime = algorithmController.getMaxValue();
        double currentTime = algorithmController.currentGaugeValue();

        customGauge = CustomGauge.getInstance();
        customGauge.setMaxValue((int) totalTime);
        customGauge.changeValue((int) currentTime);
        customGauge.startTimer();
        mainContent.getChildren().addAll(logoImageViewGreen, customGauge);
        
        percentageGauge = new PercentageGauge();
        percentageGauge.updateCaffeineLevel(algorithmController.getTotalCaffeineForDay
                (UserController.getInstance().getId()));
        mainContent.getChildren().add(percentageGauge);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(btn_profile, btn_statistics, btn_info, btn_logOut);

        VBox buttonVBox = new VBox();
        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.setSpacing(10);
        buttonVBox.getChildren().addAll(btn_logBeverage, buttonBox);

        BorderPane.setMargin(mainContent, new Insets(10));
        BorderPane.setMargin(buttonVBox, new Insets(30));

        borderPane.setCenter(mainContent);
        borderPane.setBottom(buttonVBox);
    }

    /**
     * Sets the event handlers for UI controls on the home page.
     *
     * @author Ida Nordenswan, Kenan Al Tal, Alanah Coleman
     */
    @Override
    public void setEvents() {
        for (JFXButton button : new JFXButton[]{btn_info, btn_logBeverage, btn_profile, btn_statistics, btn_logOut}) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (button == btn_info) {
                        changePage(new InfoPage(true));
                    } else if (button == btn_logBeverage) {
                        changePage(new BeverageMenuPage());
                    } else if (button == btn_profile) {
                        changePage(new ProfilePage());
                    } else if (button == btn_statistics) {
                        changePage(new StatisticsPage());
                    } else if (button == btn_logOut) {
                        changePage(new LogInPage());
                    }
                }
            });
        }
    }

    /**
     * Sets the style and icons for the buttons on the home page.
     *
     * @author Louis Brown
     */
    private void setButtons() {
        btn_statistics = new JFXButton("Statistics");
        btn_info = new JFXButton("About");
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

    /**
     * Sets the FontAwesome icon for a button.
     *
     * @param iconType The FontAwesome icon type.
     * @param button   The button to set the icon for.
     * @author Louis Brown
     */
    private void setIcon(FontAwesomeIcon iconType, JFXButton button) {
        Text temp = GlyphsDude.createIcon(iconType, "2em");
        temp.setFill(Color.WHITE);
        button.setGraphic(temp);
    }

    /**
     * Defines the CSS style for the log beverage button.
     *
     * @return The CSS style string for the log beverage button.
     * @author Louis Brown
     */
    private String beverageButtonStyle() {
        String style = "-fx-background-color:\n" +
                "#090a0c,\n" +
                "linear-gradient(#8fbc8f 0%, #8fbc8f 20%, #8fbc8f 100%),\n" +
                "linear-gradient(#8fbc8f, #8fbc8f),\n" +
                "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "-fx-background-radius: 5,4,3,5;\n" +
                "-fx-background-insets: 0,1,2,0;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "-fx-font-family: \"Arial\";\n" +
                "-fx-text-fill: linear-gradient(black, black);\n" +
                "-fx-font-size: 25px;\n" +
                "-fx-padding: 10 20 10 20;";
        return style;
    }
}