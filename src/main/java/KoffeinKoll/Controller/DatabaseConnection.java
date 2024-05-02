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
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

