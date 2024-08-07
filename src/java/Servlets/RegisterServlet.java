package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/fwrp";
    private static final String JDBC_USER = "Mishi";
    private static final String JDBC_PASSWORD = "Mishi@2022";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Retrieve form parameters
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String userType = request.getParameter("user_type");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                out.println("<h2>Driver not found: " + e.getMessage() + "</h2>");
                return;
            }

            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                String sql = "INSERT INTO users (username, password, email, user_type) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    pstmt.setString(3, email);
                    pstmt.setString(4, userType);
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<h2>Database error: " + e.getMessage() + "</h2>");
                return;
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Registration Result</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Registration Successful!</h1>");
            out.println("<UL>");
            out.println("  <LI><B>Username</B>: " + username + "</LI>");
            out.println("  <LI><B>Email</B>: " + email + "</LI>");
            out.println("  <LI><B>User Type</B>: " + userType + "</LI>");
            out.println("</UL>");
            out.println("</body>");
            out.println("</html>");

            response.sendRedirect("login.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for handling user registration";
    }
}
