package KoffeinKoll.View;

import KoffeinKoll.Controller.CaffeineCalculator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PercentageGauge extends StackPane {
    private Label titleLabel;
    private ProgressBar progressBar;
    private Label percentageLabel;
    private double recommendedAmount;


    public PercentageGauge() {
       // this.recommendedAmount =

        // Title label
        titleLabel = new Label("Recommended Caffeine");
        titleLabel.setFont(Font.font("Arial", 14));
        titleLabel.setTextFill(Color.DARKGREEN);

        // Progress bar
        progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);
        progressBar.setProgress(0);

        // Percentage label
        percentageLabel = new Label("0%");
        percentageLabel.setFont(Font.font("Arial", 14));
        percentageLabel.setTextFill(Color.DARKGREEN);

        // VBox to hold the labels
        VBox labelsBox = new VBox(titleLabel, progressBar, percentageLabel);
        labelsBox.setAlignment(Pos.CENTER);
        labelsBox.setSpacing(10);

        // Set up StackPane
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        getChildren().add(labelsBox);
    }

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
