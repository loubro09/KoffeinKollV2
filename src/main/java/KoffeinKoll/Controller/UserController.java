package KoffeinKoll.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private int id;
    private String username;
    private double weight;
    private DatabaseConnection databaseConnection;

    public UserController() {
        this.id = id;
        this.username = username;
        this.weight = weight;
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public double getWeight() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        double weight = 0;

        try {
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT weight FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                weight = resultSet.getDouble("weight");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Stäng resurser
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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
        return weight;
    }

    public int getId() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int id = 0;

        try {
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT user_id FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("user_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Stäng resurser
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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
        return id;
    }


}
