package KoffeinKoll.Controller;

import java.io.*;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * The DatabaseConnection class manages the connection to the database.
 * @author Alanah Coleman
 */
public class DatabaseConnection {
    private String url;
    private String username;
    private String password;
    private static DatabaseConnection instance;

    /**
     * Constructs a new DatabaseConnection object.
     */
    private DatabaseConnection() {
        loadConfiguration();
    }

    /**
     * Retrieves the instance of DatabaseConnection.
     * @return The instance of DatabaseConnection.
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Establishes a connection to the database.
     * @return The database connection.
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DatabaseConnection : getConnection : Connection exception");
            return null;
        }
    }

    /**
     * Loads the database configuration from the configuration file.
     */
    private void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            url = props.getProperty("db.url");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("DatabaseConnection : loadConfiguration : File not found exception");
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("DatabaseConnection : loadConfiguration : IO exception");
            throw new RuntimeException(e);
        }
    }
}

