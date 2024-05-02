package KoffeinKoll.Controller;

import javafx.scene.control.Alert;

import java.sql.*;

/**
 * The CreateUserController class handles operations related to creating new users in the KoffeinKoll application.
 */
public class CreateUserController extends A_Controller{

    private DatabaseConnection databaseConnection;

    /**
     * Constructs a new CreateUserController object.
     * @author                                                                                          //AUTHOR
     */
    public CreateUserController() {
        this.databaseConnection = databaseConnection.getInstance();
    }

    /**
     * Creates a new user with the provided information.
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @param habit    The habit of the new user.
     * @param weight   The weight of the new user.
     * @param birthday The birthday of the new user.
     * @return True if the user was successfully created, false otherwise.
     * @author                                                                                          //AUTHOR
     */
    public boolean createUser(String username, String password, String habit, double weight, String birthday) {
        // Validate input
        if (!isValidPassword(password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Password must contain at least 8 characters, one capital letter, and one number.");
            alert.show();
            return false;
        }

        if(!isUsernameValid(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Username must be between 3 and 15 characters long.");
            alert.show();
            return false;
        }

        if (!isUniqueUsername(username)) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("This username is already chosen. Try another one!");
            errorAlert.show();
            return false;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseConnection.getConnection();
            // Since user_id is now SERIAL, it will be generated automatically by the database
            preparedStatement = connection.prepareStatement("INSERT INTO users (username, habit, weight, birthdate, password) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, habit);
            preparedStatement.setDouble(3, weight);

            // Convert string date to java.sql.Date object
            Date date = Date.valueOf(birthday);
            preparedStatement.setDate(4, date);
            preparedStatement.setString(5, password); // Ensure password is set


            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                System.out.println("Failed to create user.");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
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
    }


    /**
     * Checks if a password is valid.
     * @param password The password to validate.
     * @return True if the password is valid, false otherwise.
     * @author                                                                                          //AUTHOR
     */
    private boolean isValidPassword(String password) {
        //Password must contain at least 8 characters, one capital letter, and one number
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }

    /**
     * Checks if a username is valid.
     * @param username The username to validate.
     * @return True if the username is valid, false otherwise.
     * @author Louis Brown
     */
    private boolean isUsernameValid(String username) {
        //Username must be between 3 and 15 characters
        return username != null && username.length() >= 3 && username.length() <= 15;
    }

    /**
     * Checks if a username is unique.
     * @param username   The username to check.
     * @param connection The database connection.
     * @return True if the username is unique, false otherwise.
     * @author Louis Brown
     */
    private boolean isUniqueUsername(String username, Connection connection) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            //Check if the username already exists
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; //If count is 0, username is unique; otherwise, it already exists
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //closeResources(null, preparedStatement, null);
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}