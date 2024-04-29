package KoffeinKoll.View;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.util.HashMap;
import java.util.Map;

public class BeverageMenuPage extends A_Page{
    private Map<String, Integer> beverageIdMap;
    private Label lbl_subTitle;
    private JFXButton btn_coffee;
    private JFXButton btn_espresso1;
    private JFXButton btn_espresso2;
    private JFXButton btn_tea;
    private JFXButton btn_mate;
    private JFXButton btn_energyDrink;
    private JFXButton btn_soda;
    private JFXButton goHome;

    @Override
    public void initializeUI() {
        setComponents();
        makeMap();
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
        goHome.setOnAction(e -> goHome());
    }

    @Override
    public void setScene() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.add(lbl_subTitle, 0, 0);
        GridPane.setHalignment(lbl_subTitle, HPos.CENTER);
        // Add the buttons to the grid
        gridPane.add(btn_coffee, 0, 1);
        gridPane.add(btn_espresso1, 0, 2);
        gridPane.add(btn_espresso2, 0, 3);
        gridPane.add(btn_tea, 0, 4);
        gridPane.add(btn_mate, 0, 5);
        gridPane.add(btn_energyDrink, 0, 6);
        gridPane.add(btn_soda, 0, 7);

        // Create the main layout pane and add components
        borderPane.setPadding(new Insets(20));
        borderPane.setTop(lbl_title);
        BorderPane.setAlignment(lbl_title, Pos.CENTER);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        // Create a horizontal box for the 'Go Back' button
        HBox buttonHBox = new HBox(20);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(goHome);
        borderPane.setBottom(buttonHBox);
    }

    private void setLabels() {
        lbl_subTitle = setLabelStyle("  Let's get started!\nChoose your drink:");
        lbl_subTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 23));
    }

    private void setButtons() {
        btn_coffee = createBeverageButton("Regular Coffee");
        btn_espresso1 = createBeverageButton("Single Shot Espresso");
        btn_espresso2 = createBeverageButton("Double Shot Espresso");
        btn_tea = createBeverageButton("Tea");
        btn_mate = createBeverageButton("Mate");
        btn_energyDrink = createBeverageButton("Energy Drink");
        btn_soda = createBeverageButton("Soda");

        // Create a button to go back
        goHome = new JFXButton("Go Back");
        goHome.setStyle(setButtonStyle());
    }

    private void goHome() {
        changePage(new HomePage());
    }

    private JFXButton createBeverageButton(String beverageName) {
        JFXButton btn_temp = new JFXButton(beverageName);
        btn_temp.setStyle(setButtonStyle());
        btn_temp.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHalignment(btn_temp, HPos.CENTER);
        btn_temp.setOnAction(e -> handleBeverageSelection(beverageName));
        return btn_temp;
    }

    private void handleBeverageSelection(String beverageName) {
        int beverageId = beverageIdMap.get(beverageName);
        changePage(new BeverageStatsPage(beverageId));
    }

    private void makeMap() {
        beverageIdMap = new HashMap<>();
        beverageIdMap.put("Regular Coffee", 1);
        beverageIdMap.put("Single Shot Espresso", 2);
        beverageIdMap.put("Double Shot Espresso", 3);
        beverageIdMap.put("Tea", 4);
        beverageIdMap.put("Mate", 5);
        beverageIdMap.put("Energy Drink", 6);
        beverageIdMap.put("Soda", 7);
    }
}
