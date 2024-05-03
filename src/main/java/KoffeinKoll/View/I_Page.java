package KoffeinKoll.View;

/**
 * The interface I_Page defines the contract for common page functionalities in the KoffeinKoll application.
 * Classes implementing this interface are expected to provide methods for creating, styling, and managing UI components.
 * @author Louis Brown
 */
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