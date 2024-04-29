package KoffeinKoll.Controller;

import javafx.scene.control.Alert;

import java.sql.*;

public class CreateUserController {

    private DatabaseConnection databaseConnection;

    public CreateUserController() {
        this.databaseConnection = databaseConnection.getInstance();
    }

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

    private boolean isValidPassword(String password) {
        // Password must contain at least 8 characters, one capital letter, and one number
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }

    private boolean isUsernameValid(String username) {
        return username != null && username.length() >= 3 && username.length() <= 15;
    }

    private boolean isUniqueUsername(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check if the username already exists
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; // If count is 0, username is unique; otherwise, it already exists
            }
            return true; // Default to true if no result is returned (shouldn't happen)
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources
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
}
