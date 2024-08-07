package repository.impl;

import repository.UserDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConcreteUserDAO implements UserDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/fwrp";
    private static final String JDBC_USER = "Mishi";
    private static final String JDBC_PASSWORD = "Mishi@2022";

    @Override
    public void registerUser(String username, String email, String password, String userType) {
        String sql = "INSERT INTO users (username, email, password, user_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, userType);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Implement other methods as needed
}
