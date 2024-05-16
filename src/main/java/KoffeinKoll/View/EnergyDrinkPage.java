package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class EnergyDrinkPage extends A_Page {
    private Map<String, Integer> energyDrinkIdMap;
    private Label lbl_subTitle;
    private JFXButton btn_redbull;
    private JFXButton btn_powerking;
    private JFXButton btn_prime;
    private JFXButton btn_celsius;
    private JFXButton btn_monster;
    private JFXButton btn_nocco;
    private JFXButton btn_goBack;
    private JFXButton btn_goHome;

    public EnergyDrinkPage(Map<String, Integer> energyDrinkIdMap) {
        this.energyDrinkIdMap = energyDrinkIdMap;
    }

    /**
     * Initializes the UI components.
     *
     * @author Louis Brown
     */
    @Override
    public void initializeUI() {
        setComponents();
        makeMap();
        setEvents();
        setScene();
    }

    /**
     * Sets up UI components.
     *
     * @author Louis Brown
     */
    @Override
    public void setComponents() {
        setLabels();
        setButtons();
    }

    /**
     * Sets event handlers for buttons.
     *
     * @author Ida Nordenswan                                                                                        //AUTHOR
     */
    @Override
    public void setEvents() {
        btn_goBack.setOnAction(e -> goBack());
        btn_goHome.setOnAction(e -> goHome());
    }

    /**
     * Sets up the scene layout.
     *
     * @author Ida Nordenswan                                                                                        //AUTHOR
     */
    @Override
    public void setScene() {
        VBox mainContent = new VBox();
        mainContent.setAlignment(Pos.TOP_CENTER);

        Image logoImageGreen = new Image (getClass().getResourceAsStream("/Koffeinkoll_green_green.png"));
        ImageView logoImageViewGreen = new ImageView(logoImageGreen);
        logoImageViewGreen.setFitHeight(220);
        logoImageViewGreen.setFitWidth(220);
        borderPane.setCenter(mainContent);

        mainContent.getChildren().add(logoImageViewGreen);



        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setVgap(10);
        gridPane.add(logoImageViewGreen, 0, 0);
        GridPane.setHalignment(logoImageViewGreen, HPos.CENTER);

        gridPane.add(btn_redbull, 0, 1);
        gridPane.add(btn_powerking, 0, 2);
        gridPane.add(btn_prime, 0, 3);
        gridPane.add(btn_celsius, 0, 4);
        gridPane.add(btn_monster, 0, 5);
        gridPane.add(btn_nocco, 0, 6);

        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        HBox buttonHBox = new HBox(20,btn_goBack, btn_goHome);
        buttonHBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonHBox, 0, 18);
    }

    /**
     * Sets up labels.
     *
     * @author Ida Nordenswan                                                                                         //AUTHOR
     */
    private void setLabels() {
        lbl_subTitle = setLabelStyle("  Choose your energy drink:");
        lbl_subTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 23));
    }

    /**
     * Sets buttons.
     *
     * @author Ida Nordenswan
     */
    private void setButtons() {
        btn_redbull = createBeverageButton("Redbull");
        btn_powerking = createBeverageButton("Powerking");
        btn_prime = createBeverageButton("Prime");
        btn_celsius = createBeverageButton("Celsius");
        btn_monster = createBeverageButton("Monster");
        btn_nocco = createBeverageButton("Nocco");

        btn_goBack = new JFXButton("Go Back");
        btn_goBack.setStyle(setButtonStyle());

        btn_goHome = new JFXButton("Home");
        btn_goHome.setStyle(setButtonStyle());
    }

    /**
     * Going back to the home page.
     *
     * @author Ida Nordenswan.                                                                                     //AUTHOR
     */
    private void goBack() {
        changePage(new BeverageMenuPage());
    }

    /**
     * Creates a beverage button with the specified name.
     *
     * @param beverageName beverage name.
     * @return JFXButton.
     * @author Ida Nordenswan                                                                                        //AUTHOR
     */
    private JFXButton createBeverageButton(String beverageName) {
        JFXButton btn_temp = new JFXButton(beverageName);
        btn_temp.setStyle(setButtonStyle());
        btn_temp.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHalignment(btn_temp, HPos.CENTER);
        btn_temp.setOnAction(e -> handleBeverageSelection(beverageName));
        return btn_temp;
    }

    /**
     * Handles the selection of a beverage.
     *
     * @param beverageName The name of the selected beverage.
     * @author Ida Nordenswan                                                                                    //AUTHOR
     */
    private void handleBeverageSelection(String beverageName) {
        int beverageId = energyDrinkIdMap.get(beverageName);
        changePage(new BeverageStatsPage(beverageId));
    }

    /**
     * Going back to the home page.
     *
     * @author Ida Nordenswan.                                                                                     //AUTHOR
     */
    private void goHome() {
        changePage(new HomePage());
    }

    /**
     * Initializes the beverage ID map.
     *
     * @author Ida Nordenswan                                                                                       //AUTHOR
     */
    private void makeMap() {
        energyDrinkIdMap = new HashMap<>();
        energyDrinkIdMap.put("Redbull", 13);
        energyDrinkIdMap.put("Powerking", 14);
        energyDrinkIdMap.put("Prime", 15);
        energyDrinkIdMap.put("Celsius", 16);
        energyDrinkIdMap.put("Monster", 17);
        energyDrinkIdMap.put("Nocco", 18);
    }
}
