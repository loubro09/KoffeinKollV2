package KoffeinKoll.View;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public interface I_Page {
    void initializeUI();
    void createScene(BorderPane borderPane);
    void setBackground(Scene scene);
    String setButtonStyle();
}
