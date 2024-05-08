package KoffeinKoll.View;

import KoffeinKoll.Controller.BeverageController;
import KoffeinKoll.Controller.CaffeineCalculator;
import KoffeinKoll.Controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class customs the JavaFX control representing a percentage gauge for displaying caffeine levels.
 * This is visually shown in the home page.
 * @author Alanah Coleman
 */
public class PercentageGauge extends StackPane {
    private Label lbl_title;
    private Label lbl_percentage;
    private ProgressBar progressBar;
    private double recommendedAmount;
    private CaffeineCalculator caffeineCalculator;
    private BeverageController beverageController;
    private String userName;


    /**
     * Constructs percentage gauge.
     * Creating the functions and setting the labels for the gauge.
     */
    public PercentageGauge() {
        this.userName = UserController.getInstance().getUsername();
        this.beverageController = new BeverageController();
        this.caffeineCalculator = new CaffeineCalculator();
        this.recommendedAmount = caffeineCalculator.getRecommendedDose();

        lbl_title = new Label("Recommended Caffeine: " + recommendedAmount);
        lbl_title.setFont(Font.font("Arial", 14));
        lbl_title.setTextFill(Color.DARKGREEN);

        progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);
        progressBar.setProgress(0);

        lbl_percentage = new Label("0%");
        lbl_percentage.setFont(Font.font("Arial", 14));
        lbl_percentage.setTextFill(Color.DARKGREEN);

        VBox labelsBox = new VBox(lbl_title, progressBar, lbl_percentage);
        labelsBox.setAlignment(Pos.CENTER);
        labelsBox.setSpacing(10);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        getChildren().add(labelsBox);
    }

    /**
     * Updates the caffeine level display based on the current caffeine amount.
     *
     * @param currentAmount The current amount of caffeine consumed.
     */
    public void updateCaffeineLevel(double currentAmount) {
        if (currentAmount >= recommendedAmount) {
            progressBar.setProgress(1);
            lbl_percentage.setText("100%");
        } else {
            double percentage = (currentAmount / recommendedAmount) * 100;
            progressBar.setProgress(currentAmount / recommendedAmount);
            lbl_percentage.setText(String.format("%.1f%%", percentage));
        }
    }
}
