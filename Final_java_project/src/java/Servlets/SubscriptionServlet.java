package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/subscribe")
public class SubscriptionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fwrp";
    private static final String DB_USER = "Mishi";
    private static final String DB_PASSWORD = "Mishi@2022";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String location = request.getParameter("location");
        String preference = request.getParameter("preference");
        String communicationMethod = request.getParameter("communicationMethod");
        String userType = request.getParameter("userType");

        String message;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO subscriptions (username, email, phone, location, preference, communication_method, user_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, location);
                stmt.setString(5, preference);
                stmt.setString(6, communicationMethod);
                stmt.setString(7, userType);

                int rowsAffected = stmt.executeUpdate();
                message = rowsAffected > 0 ? "Subscription successful!" : "Failed to subscribe.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error processing subscription: " + e.getMessage();
        }

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            out.println("{\"message\":\"" + message + "\"}");
        }
    }
}
