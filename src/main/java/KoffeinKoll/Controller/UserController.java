package KoffeinKoll.Controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The UserController class manages user-related operations such as retrieving user information from the database.
 */
public class UserController {
    private int id;
    private String username;
    private double weight;
    private String habit;
    private DatabaseConnection databaseConnection;
    private static UserController instance; // Singleton instance

    /**
     * Constructor for UserController
     * @author
     */
    public UserController() {
        this.id = id;
        this.username = username;
        this.weight = weight;
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    /**
     * Retrieves the singleton instance of UserController.
     * @return The singleton instance of UserController.
     * @author LouisBrown
     */
    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username to set.
     * @author alanahColeman
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the username of the user.
     * @return The username of the user.
     * @author alanahColeman
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the weight of the user from the database.
     * @return The weight of the user.
     * @author alanahColeman
     */
    public double getWeight() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT weight FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Set the weight field to the retrieved value from the database
                weight = resultSet.getDouble("weight");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources
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

    /**
     * Retrieves the user ID from the database.
     * @return The user ID.
     * @author alanahColeman
     */
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


    /**
     * Retrieves the habit of the user from the database.
     * @return The habit from the user.
     */
    public String getHabit() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT habit FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Set the habit field to the retrieved value from the database
                habit = resultSet.getString("habit");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources
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
        System.out.println("Habit: " + habit);
        return habit;
    }
}
