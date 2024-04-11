package KoffeinKoll.Controller;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import KoffeinKoll.Controller.DatabaseConnection;

public class LoginController {

    private DatabaseConnection databaseConnection;

    public LoginController() {
        this.databaseConnection = new DatabaseConnection();
    }

    public boolean logIn(String username, String password) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User not found");
                alert.show();
            }
            else {
                while(resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        return true;
                    }
                    else {
                        System.out.println("Wrong password");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Wrong password");
                        alert.show();
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
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
}
