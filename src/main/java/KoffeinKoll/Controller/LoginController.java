package KoffeinKoll.Controller;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The LoginController class manages user login functionality.
 */
public class LoginController {

    private DatabaseConnection databaseConnection;
    private UserController user;

    /**
     * Constructs a new LoginController object.
     * @author Alanah Coleman
     */
    public LoginController() {
        this.databaseConnection = databaseConnection.getInstance();
    }

    /**
     * Attempts to log in a user with the provided credentials.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if the login is successful, otherwise false.
     * @author Alanah Coleman
     */
    public boolean logIn(String username, String password) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                showAlert("Error", "User not found.", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {

                        user = UserController.getInstance();
                        user.setUsername(username);

                        return true;
                    } else {
                        showAlert("Error", "Wrong password.", Alert.AlertType.ERROR);
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("LoginController : Login : SQLException");
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("LoginController : LogIn : closing ResultSet");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("LoginController : LogIn : closing PreparedStatement");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("LoginController : LogIn : closing Connection");

                }
            }
        }
        return false;
    }

    /**
     * Shows an alert dialog with the specified title and content.
     *
     * @param title     The title of the alert dialog.
     * @param content   The content of the alert dialog.
     * @param alertType The type of the alert
     * @author Louis Brown
     */
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}