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
 */
public class PercentageGauge extends StackPane {
    private Label titleLabel;
    private ProgressBar progressBar;
    private Label percentageLabel;
    private double recommendedAmount;
    private CaffeineCalculator caffeineCalculator;
    private BeverageController beverageController;
    private UserController userController;
    private String userName;


    /**
     * Constructs percentage gauge.
     * Creating the functions and setting the labels for the gauge.
     * @author alanahcoleman
     */
    public PercentageGauge() {
        this.userName = UserController.getInstance().getUsername();
        this.beverageController = new BeverageController();
        this.userController = UserController.getInstance();
        this.caffeineCalculator = new CaffeineCalculator(userController, beverageController);
        this.recommendedAmount = caffeineCalculator.getRecommendedDose();

        titleLabel = new Label("Recommended Caffeine: " + recommendedAmount);
        titleLabel.setFont(Font.font("Arial", 14));
        titleLabel.setTextFill(Color.DARKGREEN);

        progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);
        progressBar.setProgress(0);

        percentageLabel = new Label("0%");
        percentageLabel.setFont(Font.font("Arial", 14));
        percentageLabel.setTextFill(Color.DARKGREEN);

        VBox labelsBox = new VBox(titleLabel, progressBar, percentageLabel);
        labelsBox.setAlignment(Pos.CENTER);
        labelsBox.setSpacing(10);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        getChildren().add(labelsBox);
    }

    /**
     * Updates the caffeine level display based on the current caffeine amount.
     * @param currentAmount The current amount of caffeine consumed.
     * @author alanahcoleman
     */
    public void updateCaffeineLevel(double currentAmount) {
        if (currentAmount >= recommendedAmount) {
            progressBar.setProgress(1);
            percentageLabel.setText("100%");
        } else {
            double percentage = (currentAmount / recommendedAmount) * 100;
            progressBar.setProgress(currentAmount / recommendedAmount);
            percentageLabel.setText(String.format("%.1f%%", percentage));
        }
    }
}
