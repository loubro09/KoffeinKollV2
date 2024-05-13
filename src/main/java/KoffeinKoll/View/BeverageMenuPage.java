package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
 * BeverageMenuPage class represents the beverage menu page where users can choose their drinks.
 * It extends A_Page and implements methods to initialize UI components, set event handlers, and set the scene.
 */
public class BeverageMenuPage extends A_Page {
    private Map<String, Integer> beverageIdMap;
    private Label lbl_subTitle;
    private JFXButton btn_coffee;
    private JFXButton btn_espresso1;
    private JFXButton btn_espresso2;
    private JFXButton btn_tea;
    private JFXButton btn_mate;
    private JFXButton btn_energyDrink;
    private JFXButton btn_nocco;
    private JFXButton btn_soda;
    private JFXButton goHome;

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
        goHome.setOnAction(e -> goHome());
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

        gridPane.add(btn_coffee, 0, 1);
        gridPane.add(btn_espresso1, 0, 2);
        gridPane.add(btn_espresso2, 0, 3);
        gridPane.add(btn_tea, 0, 4);
        gridPane.add(btn_mate, 0, 5);
        gridPane.add(btn_energyDrink, 0, 6);
        gridPane.add(btn_nocco,0,7);
        gridPane.add(btn_soda, 0, 8);

        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);




        HBox buttonHBox = new HBox(20);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(goHome);
        borderPane.setBottom(buttonHBox);
    }

    /**
     * Sets up labels.
     *
     * @author Ida Nordenswan                                                                                         //AUTHOR
     */
    private void setLabels() {
        lbl_subTitle = setLabelStyle("  Let's get started!\nChoose your drink:");
        lbl_subTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 23));
    }

    /**
     * Sets buttons.
     *
     * @author Ida Nordenswan
     */
    private void setButtons() {
        btn_coffee = createBeverageButton("Regular Coffee");
        btn_espresso1 = createBeverageButton("Single Shot Espresso");
        btn_espresso2 = createBeverageButton("Double Shot Espresso");
        btn_tea = createBeverageButton("Tea");
        btn_mate = createBeverageButton("Mate");
        btn_energyDrink = createBeverageButton("Energy Drink");
        btn_nocco = createBeverageButton("Nocco");
        btn_soda = createBeverageButton("Soda");

        goHome = new JFXButton("Home");
        goHome.setStyle(setButtonStyle());
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
        int beverageId = beverageIdMap.get(beverageName);
        changePage(new BeverageStatsPage(beverageId));
    }

    /**
     * Initializes the beverage ID map.
     *
     * @author Ida Nordenswan                                                                                       //AUTHOR
     */
    private void makeMap() {
        beverageIdMap = new HashMap<>();
        beverageIdMap.put("Regular Coffee", 1);
        beverageIdMap.put("Single Shot Espresso", 2);
        beverageIdMap.put("Double Shot Espresso", 3);
        beverageIdMap.put("Tea", 4);
        beverageIdMap.put("Mate", 5);
        beverageIdMap.put("Energy Drink", 6);
        beverageIdMap.put("Soda", 7);
        beverageIdMap.put("Nocco",8);
    }
}
