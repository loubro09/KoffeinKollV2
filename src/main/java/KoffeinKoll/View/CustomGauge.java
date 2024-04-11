package KoffeinKoll.View;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.BarSkin;
import eu.hansolo.medusa.skins.FlatSkin;
import eu.hansolo.medusa.skins.SlimSkin;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class CustomGauge extends StackPane {
    private Gauge gauge;

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

    public Gauge getGauge() {
        return gauge;
    }

}
