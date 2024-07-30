package Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;

    static {
        Properties properties = new Properties();
        String jarPath = Paths.get("").toAbsolutePath().toString();  // Get the directory where the JAR is located
        String configFilePath = Paths.get(jarPath, "credenziali_database.properties").toString();

        try (InputStream input = new FileInputStream(configFilePath)) {
            properties.load(input);
            DB_URL = properties.getProperty("db.url");
            DB_USERNAME = properties.getProperty("db.username");
            DB_PASSWORD = properties.getProperty("db.password");
        } catch (IOException ex) {
            System.err.println("Error loading configuration file from " + configFilePath);
            ex.printStackTrace();
        }
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}