package KoffeinKoll.View;

import KoffeinKoll.Controller.BeverageController;
import KoffeinKoll.Controller.CaffeineCalculator;
import KoffeinKoll.Controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * This class customs the JavaFX control representing a percentage gauge for displaying caffeine levels.
 * This is visually shown in the home page.
 * @author Alanah Coleman
 */
public class PercentageGauge extends StackPane {
    private Label lbl_title;
    private Label lbl_percentage;
    private ProgressBar progressBar;
    private int recommendedAmount;
    private CaffeineCalculator caffeineCalculator;

    /**
     * Constructs percentage gauge.
     * Creating the functions and setting the labels for the gauge.
     */
    public PercentageGauge() {
        this.caffeineCalculator = new CaffeineCalculator();
        this.recommendedAmount = caffeineCalculator.getRecommendedDose();

        int cupOfCoffees = recommendedAmount / 92;

        lbl_title = new Label("Recommended Daily Caffeine Intake: " + recommendedAmount + " mg");
        lbl_title.setFont(Font.font("Arial", 14));
        lbl_title.setTextFill(Color.DARKGREEN);

        String cups = "";
        if (cupOfCoffees > 1) {
            cups = " cups ";
        }
        else {
            cups = " cup ";
        }
        Tooltip tooltip1 = new Tooltip("This is about " + cupOfCoffees + cups + "of coffee.");
        tooltip1.setShowDelay(Duration.millis(10));
        tooltip1.setShowDuration(Duration.seconds(5));
        Tooltip.install(lbl_title, tooltip1);

        progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);
        progressBar.setProgress(0);

        lbl_percentage = new Label("0%");
        lbl_percentage.setFont(Font.font("Arial", 14));
        lbl_percentage.setTextFill(Color.DARKGREEN);

        Tooltip tooltip2 = new Tooltip("This is how much of your daily recommended intake you have " +
                "consumed today.");
        tooltip2.setShowDelay(Duration.millis(10));
        tooltip2.setShowDuration(Duration.seconds(5));
        Tooltip.install(lbl_percentage, tooltip2);

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
            lbl_percentage.setText(String.format("%.0f%%", percentage));
        }
    }
}
