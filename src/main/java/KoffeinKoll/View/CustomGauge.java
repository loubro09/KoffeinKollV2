package KoffeinKoll.View;

import KoffeinKoll.Controller.AlgorithmController;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.SlimSkin;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class CustomGauge extends StackPane {
    private Gauge gauge;
    private AlgorithmController algorithmController;
    private Timeline timeline;
    private static CustomGauge instance; // Singleton instance
    private int maxValue;
    private Label label;

    public CustomGauge() {
        this.algorithmController = new AlgorithmController();

        // Create a Gauge with Bar skin
        gauge = new Gauge();
        gauge.setSkin(new SlimSkin(gauge));
        gauge.setBarColor(Color.DARKSEAGREEN);
        gauge.setTitle("Time Countdown: ");


        gauge.setMinValue(0);
        gauge.setMaxValue(0);
        gauge.setValue(0); // Set initial value
        gauge.setAnimated(true); // Enable animation

       timeline = new Timeline(
                new KeyFrame(Duration.hours(1), e->{
                    int remainingTime = (int) gauge.getValue();
                    if (remainingTime>0){
                        gauge.setValue(remainingTime-1);
                    }else {
                        timeline.stop();
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);

        // Add the gauge to the StackPane
        getChildren().add(gauge);
    }
    public void startTimer(){
        timeline.play();
    }

    public void stopTimer(){
        timeline.stop();
    }

    public static CustomGauge getInstance() {
        if (instance == null) {
            instance = new CustomGauge();
        }
        return instance;
    }

    public void setMaxValue(int maxValue) {
        System.out.println(maxValue);
        gauge.setMaxValue(maxValue);
    }

    public void changeValue(int value) {
       gauge.setValue(value);
    }

    public double getCurrentValue() { return gauge.getValue();}
}
