package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/retailerDashboard")
public class RetailerDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fwrp";
    private static final String DB_USER = "Mishi";
    private static final String DB_PASSWORD = "Mishi@2022";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null || !"retailer".equals(session.getAttribute("userType"))) {
            response.sendRedirect("login.html");
            return;
        }

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addItem(request, response);
        } else if ("update".equals(action)) {
            updateItem(request, response);
        } else if ("markSurplus".equals(action)) {
            markSurplus(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemName = request.getParameter("itemName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String expirationDate = request.getParameter("expirationDate");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO inventory (item_name, quantity, expiration_date) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, itemName);
                stmt.setInt(2, quantity);
                stmt.setString(3, expirationDate);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.getWriter().write("Item added successfully!");
                } else {
                    response.getWriter().write("Failed to add item.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error adding item: " + e.getMessage());
        }
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE inventory SET quantity = ? WHERE item_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, quantity);
                stmt.setInt(2, itemId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.getWriter().write("Item updated successfully!");
                } else {
                    response.getWriter().write("Failed to update item.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error updating item: " + e.getMessage());
        }
    }

    private void markSurplus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        boolean forDonation = request.getParameter("forDonation") != null;
        boolean forSale = request.getParameter("forSale") != null;
        double price = forSale ? Double.parseDouble(request.getParameter("price")) : 0.0;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE inventory SET is_for_donation = ?, is_for_sale = ?, price = ? WHERE item_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, forDonation);
                stmt.setBoolean(2, forSale);
                stmt.setDouble(3, price);
                stmt.setInt(4, itemId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.getWriter().write("Item marked as surplus successfully!");
                } else {
                    response.getWriter().write("Failed to mark item as surplus.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error marking item as surplus: " + e.getMessage());
        }
    }
}
