package KoffeinKoll.Controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * The DatabaseConnection class manages the connection to the database.
 */
public class DatabaseConnection {
    private String url;
    private String username;
    private String password;
    private static DatabaseConnection instance;

    /**
     * Constructs a new DatabaseConnection object.
     * @author                                                                                          //AUTHOR
     */
    private DatabaseConnection() {
        loadConfiguration();
    }

    /**
     * Retrieves the instance of DatabaseConnection.
     * @return The instance of DatabaseConnection.
     * @author                                                                                          //AUTHOR
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Loads the database configuration from the configuration file.
     * @author                                                                                          //AUTHOR
     */
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

    /**
     * Establishes a connection to the database.
     * @return The database connection.
     * @author                                                                                          //AUTHOR
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

