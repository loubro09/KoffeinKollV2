package KoffeinKoll.View;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.chart.ChartData;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class CircleChart extends StackPane {
    private Tile donutChartTile;

    public CircleChart() {
        donutChartTile = new Tile();
        donutChartTile.setSkinType(SkinType.DONUT_CHART);
        donutChartTile.setTitle("Weekly Statistics");
        donutChartTile.setTitleColor(Color.rgb(0, 70, 0)); // Ställ in textfärgen
        donutChartTile.setTextColor(Color.ORANGERED);

        // Skapa data för sektorerna i cirkeldiagrammet
        List<ChartData> chartDataList = Arrays.asList(
                new eu.hansolo.tilesfx.chart.ChartData("Sector 1", 25, Color.rgb(255, 238, 194)),
                new eu.hansolo.tilesfx.chart.ChartData("Sector 2", 35, Color.rgb(255, 180, 88)),
                new eu.hansolo.tilesfx.chart.ChartData("Sector 3", 40, Color.rgb(251, 101, 20))
        );

        // Lägg till data för cirkeldiagrammet
        donutChartTile.setChartData(chartDataList);

        // Ställ in färgen på cirkeln
        donutChartTile.setBackgroundColor(Color.WHITE); // Gör bakgrunden transparent

        // Lägg till Donut Chart Tile till StackPane
        getChildren().add(donutChartTile);
    }
}
