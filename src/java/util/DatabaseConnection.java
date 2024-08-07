package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/fwrp";
    private static final String JDBC_USER = "Mishi";
    private static final String JDBC_PASSWORD = "Mishi@2022";

    public static Connection getConnection() throws SQLException {
        // Establish and return a connection to the database
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public static void main(String[] args) {
        try {
            // Test the database connection
            Connection connection = getConnection();
            System.out.println("Connected to the database!");

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            e.printStackTrace();
        }
    }
}
