package KoffeinKoll.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddNewUser {

    private DatabaseConnection databaseConnection;

    public AddNewUser() {
        this.databaseConnection = new DatabaseConnection();
    }

    public void addUser(String username, String password, double length, double weight, String birthdate) {
        Connection connection = null;

        try {
            connection = databaseConnection.getConnection();

            if (connection != null) {
                // Insert query
                String insertQuery = "INSERT INTO public.users (username, password, length, weight, birthdate) VALUES (?, ?, ?, ?, ?)";
                // Creating prepared statement
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                // Setting parameters
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setDouble(3, length);
                preparedStatement.setDouble(4, weight);
                preparedStatement.setString(5, birthdate);

                // Executing the insert query
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("New user added successfully!");
                } else {
                    System.out.println("Failed to add new user!");
                }

                // Closing resources
                preparedStatement.close();
            } else {
                System.out.println("Failed to establish database connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
