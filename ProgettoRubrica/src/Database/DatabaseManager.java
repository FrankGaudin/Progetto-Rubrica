package Database;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/rubrica";
    private static final String DB_USERNAME = "root";  // Cambia se hai usato un altro username
    private static final String DB_PASSWORD = "password";  // Cambia con la tua password

    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}