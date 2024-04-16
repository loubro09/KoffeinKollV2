package KoffeinKoll.Controller;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class CreateUserController {

    private final DatabaseConnection databaseConnection;

    public CreateUserController() {
        this.databaseConnection = new DatabaseConnection();
    }

    public boolean createUser(String username, String password, double length, double weight, String birthday) {
        // Validate input
        if (!isValidPassword(password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password must contain at least 8 characters, one capital letter, and one number.");
            alert.show();
            return false;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseConnection.getConnection();
            // Since user_id is now SERIAL, it will be generated automatically by the database
            preparedStatement = connection.prepareStatement("INSERT INTO users (username, length, weight, birthdate, password) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setDouble(2, length);
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
}
