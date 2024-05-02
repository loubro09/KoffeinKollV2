package KoffeinKoll.View;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.SlimSkin;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * CustomGauge represents a custom gauge component used to visualize caffeine intake.
 * It extends StackPane and contains a Gauge from the Medusa library.
 */
public class CustomGauge extends StackPane {
    private Gauge gauge;

    /**
     * Constructs a CustomGauge object.
     * Initializes the gauge with default settings and adds it to the StackPane.
     * @author Louis Brown
     */
    public CustomGauge() {
        // Create a Gauge with Bar skin
        gauge = new Gauge();
        gauge.setSkin(new SlimSkin(gauge));
        gauge.setBarColor(Color.DARKSEAGREEN);
        gauge.setUnit("mg");
        gauge.setTitle("Caffeine");
        gauge.setMinValue(0);
        gauge.setMaxValue(100);
        gauge.setValue(50); // Set initial value
        gauge.setAnimated(true); // Enable animation

        // Add the gauge to the StackPane
        getChildren().add(gauge);
    }
}
