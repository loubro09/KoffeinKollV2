package KoffeinKoll.Controller;

import javafx.scene.control.Alert;

import java.sql.*;

/**
 * The CreateUserController class handles operations related to creating new users in the KoffeinKoll application.
 */
public class CreateUserController {

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
        if (!isValidPassword(password)) {
            showAlert("Error", "Password must contain at least 8 characters, one capital letter, " +
                    "and one number.", Alert.AlertType.ERROR);
            return false;
        }

        if(!isUsernameValid(username)) {
            showAlert("Error", "Username must be between 3 and 15 characters long.", Alert.AlertType.ERROR);
            return false;
        }


        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnection.getInstance().getConnection();

            if (!isUniqueUsername(username, connection)) {
                showAlert("Error", "This username is already chosen. Try another one!", Alert.AlertType.ERROR);
                return false;
            }

            preparedStatement = connection.prepareStatement("INSERT INTO users (username, habit, weight, " +
                    "birthdate, password) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, habit);
            preparedStatement.setDouble(3, weight);

            Date date = Date.valueOf(birthday);
            preparedStatement.setDate(4, date);
            preparedStatement.setString(5, password);


            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                System.out.println("Failed to create user.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CreateUserController : createUser : Database or SQL error.");
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("CreateUserController : createUser : Closing Prepared Statement error.");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("CreateUserController : createUser : Closing connection error.");
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
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; //If count is 0, username is unique; otherwise, it already exists
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CreateUserController : isUniqueUsername : Database or SQL error.");
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("CreateUserController : isUniqueUsername : Closing Prepared Statement error.");
                }
            }
        }
    }

    /**
     * Shows an alert dialog with the specified title and content.
     * @param title The title of the alert dialog.
     * @param content The content of the alert dialog.
     * @param alertType The type of the alert
     * @author                                                                                          //AUTHOR
     */
    protected void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}