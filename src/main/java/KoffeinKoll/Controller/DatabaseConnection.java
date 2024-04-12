package KoffeinKoll.Controller;

import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {

    private String url;
    private String username;
    private String password;
    private DatabaseConnection databaseConnection;

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    public Connection getConnection() {
        Properties props = new Properties();
        FileInputStream file = null;


        try {
            file = new FileInputStream("config.properties");
            props.load(file);
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            url = props.getProperty("db.url");
            Connection connection = DriverManager.getConnection(url,username,password);
            return connection;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

}
