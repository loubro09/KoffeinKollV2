package KoffeinKoll.View;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/*public interface I_Page {
    void initializeUI();
    void createScene(BorderPane borderPane);
    void setBackground(Scene scene);
    String setButtonStyle();
}*/
public interface I_Page {
    void createScene();
    void setWindowTitle();
    void setStageSize();
    void createBordePane();
    void setTitle();
    void setBackground();
    String setButtonStyle();
    void initializeUI();
    void setComponents();
    void setEvents();
    void setScene();
}