package KoffeinKoll.View;

import KoffeinKoll.Controller.AlgorithmController;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.SlimSkin;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * CustomGauge represents a custom gauge component used to visualize caffeine intake.
 * It extends StackPane and contains a Gauge from the Medusa library.
 */
public class CustomGauge extends StackPane {
    private Gauge gauge;
    private AlgorithmController algorithmController;
    private Timeline timeline;
    private static CustomGauge instance;


    /**
     * Constructs a CustomGauge object.
     * Initializes the gauge with default settings and adds it to the StackPane.
     *
     * @author Louis Brown, Ida Nordenswan
     */
    public CustomGauge() {
        this.algorithmController = new AlgorithmController();


        gauge = new Gauge();
        gauge.setSkin(new SlimSkin(gauge));
        gauge.setBarColor(Color.DARKSEAGREEN);
        gauge.setTitle("Time Countdown: ");


        gauge.setMinValue(0);
        gauge.setMaxValue(0);
        gauge.setValue(0);
        gauge.setDecimals(0);
        gauge.setAnimated(true);

        timeline = new Timeline(
                new KeyFrame(Duration.hours(1), e -> {
                    int remainingTime = (int) gauge.getValue();
                    if (remainingTime > 0) {
                        gauge.setValue(remainingTime - 1);
                    } else {
                        timeline.stop();
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);


        getChildren().add(gauge);
    }

    /**
     * Retrieves the singleton instance of CustomGauge.
     * @return The singleton instance of CustomGauge.
     * @author Louis Brown
     */
    public static CustomGauge getInstance() {
        if (instance == null) {
            instance = new CustomGauge();
        }
        return instance;
    }

    /**
     * Retrieves the current value displayed on the gauge.
     * @return The current value displayed on the gauge.
     * @author Ida Nordenswan
     */
    public double getCurrentValue() {
        return gauge.getValue();
    }

    /**
     * Sets the maximum value of the gauge.
     * @param maxValue The maximum value to be set for the gauge.
     * @author Ida Nordenswan
     */
    public void setMaxValue(int maxValue) {
        System.out.println(maxValue);
        gauge.setMaxValue(maxValue);
    }

    /**
     * Starts the timer associated with the gauge.
     * @author Ida Nordenswan
     */
    public void startTimer() {
        timeline.play();
    }

    /**
     * Changes the current value displayed on the gauge.
     * @param value The new value to be displayed on the gauge.
     * @author Ida Nordenswan
     */
    public void changeValue(int value) {
        gauge.setValue(value);
    }
}
