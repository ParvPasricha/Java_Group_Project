package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/fwrp";
    private static final String JDBC_USER = "Mishi";
    private static final String JDBC_PASSWORD = "Mishi@2022";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                    String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setString(1, username);
                        statement.setString(2, password);

                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                // User authenticated successfully
                                String userType = resultSet.getString("user_type").toLowerCase(); // Normalize to lowercase
                                HttpSession session = request.getSession();
                                session.setAttribute("username", username);
                                session.setAttribute("userType", userType);

                                // Redirect based on user type
                                switch (userType) {
                                    case "retailer":
                                        response.sendRedirect("retailerDashboard.html");
                                        break;
                                    case "consumer":
                                        response.sendRedirect("consumerDashboard.html");
                                        break;
                                    case "charitable":
                                        response.sendRedirect("charitableOrganizationDashboard.html");
                                        break;
                                    default:
                                        out.println("<h2>Unknown user type: " + userType + "</h2>");
                                        break;
                                }
                            } else {
                                // Authentication failed
                                response.sendRedirect("login.html?error=1");
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                out.println("<h2>Driver not found: " + e.getMessage() + "</h2>");
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<h2>Database error: " + e.getMessage() + "</h2>");
            }
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
        return "Servlet for handling user login and redirection";
    }
}
