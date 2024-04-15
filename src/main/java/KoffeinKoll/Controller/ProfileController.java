package KoffeinKoll.Controller;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class ProfileController {

    private final DatabaseConnection databaseConnection;

    public ProfileController() {
        this.databaseConnection = new DatabaseConnection();
    }

    public boolean updateUser(int userId, String newHeight, String newWeight, String newDateOfBirth) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseConnection.getConnection();

            // Construct the SQL update statement dynamically based on the provided input
            StringBuilder updateStatement = new StringBuilder("UPDATE users SET ");
            boolean hasUpdates = false;

            // Check and update height if provided
            if (!newHeight.isEmpty()) {
                updateStatement.append("length = ?, ");
                hasUpdates = true;
            }

            // Check and update weight if provided
            if (!newWeight.isEmpty()) {
                updateStatement.append("weight = ?, ");
                hasUpdates = true;
            }

            // Check and update birthdate if provided
            if (!newDateOfBirth.isEmpty()) {
                updateStatement.append("birthdate = ?, ");
                hasUpdates = true;
            }

            // Remove the trailing comma and space
            if (hasUpdates) {
                updateStatement.setLength(updateStatement.length() - 2);
                updateStatement.append(" WHERE user_id = ?");

                // Prepare the statement
                preparedStatement = connection.prepareStatement(updateStatement.toString());
                int parameterIndex = 1;

                // Set parameters based on provided input
                if (!newHeight.isEmpty()) {
                    preparedStatement.setObject(parameterIndex++, Double.parseDouble(newHeight));
                }
                if (!newWeight.isEmpty()) {
                    preparedStatement.setObject(parameterIndex++, Double.parseDouble(newWeight));
                }
                if (!newDateOfBirth.isEmpty()) {
                    preparedStatement.setDate(parameterIndex++, Date.valueOf(newDateOfBirth));
                }
                preparedStatement.setInt(parameterIndex, userId);

                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return true;
                } else {
                    System.out.println("Failed to update user information.");
                    return false;
                }
            } else {
                // No updates to perform
                System.out.println("No updates provided.");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}