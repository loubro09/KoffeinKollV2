package KoffeinKoll.Controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
    private String url;
    private String username;
    private String password;
    private static DatabaseConnection instance;

    DatabaseConnection() {
        loadConfiguration();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            url = props.getProperty("db.url");
        } catch (Exception e) {
            e.printStackTrace(); // Consider using logging here instead
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace(); // Consider using logging here instead
            return null;
        }
    }

    public double getBeverageConcentration(int beverageId){
        double concentration = 0.0;
        String query= "SELECT caffeine_concentration FROM beverages WHERE beverage_id = ?";

        try(PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setInt(1, beverageId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                concentration = resultSet.getDouble("caffeine_concentration");
            }else{
                System.out.println("beverage cannot be found in database");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return concentration;
    }
}

