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
    private String userName;

    /**
     * Constructs a new LoginController object.
     * @author alanahColeman
     */
    public LoginController() {
        this.databaseConnection = databaseConnection.getInstance();
    }

    /**
     * Attempts to log in a user with the provided credentials.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if the login is successful, otherwise false.
     * @author alanahColeman
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
                System.out.println("User not found");
                showAlert("Error", "User not found.", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {

                        System.out.println(username + " logged in");
                        user = UserController.getInstance();
                        user.setUsername(username);
                        userName = username;

                        return true;
                    } else {
                        System.out.println("Wrong password");
                        showAlert("Error", "Wrong password.", Alert.AlertType.ERROR);
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    /**
     * Shows an alert dialog with the specified title and content.
     * @param title The title of the alert dialog.
     * @param content The content of the alert dialog.
     * @param alertType The type of the alert
     * @author
     */
    protected void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}