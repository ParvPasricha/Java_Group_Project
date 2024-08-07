package Servlets;

import model.InventoryItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/inventory")
public class InventoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fwrp";
    private static final String DB_USER = "Mishi"; // Replace with your actual username
    private static final String DB_PASSWORD = "Mishi@2022"; // Replace with your actual password

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String itemName = request.getParameter("itemName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String expirationDate = request.getParameter("expirationDate");
        String message = "";

        try {
            // Explicitly load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO inventory (inventory_name, quantity, expiration_date) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, itemName);
                    stmt.setInt(2, quantity);
                    stmt.setString(3, expirationDate);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        message = "Item added successfully!";
                    } else {
                        message = "Failed to add item.";
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            message = "Database driver not found: " + e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error adding item: " + e.getMessage();
        }

        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<a href='inventory'>View Inventory</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<InventoryItem> items = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Explicitly load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT * FROM inventory";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        InventoryItem item = new InventoryItem();
                        item.setItemId(rs.getInt("inventory_id"));
                        item.setItemName(rs.getString("item_name"));
                        item.setQuantity(rs.getInt("quantity"));
                        java.sql.Date dbSqlDate = rs.getDate("expiration_date");
                        LocalDate expiration = dbSqlDate.toLocalDate();

                        if (!expiration.isAfter(nextWeek)) {
                            item.setSurplus(true);
                        } else {
                            item.setSurplus(false);
                        }

                        items.add(item);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println("Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("SQL Error: " + e.getMessage());
        }

        out.println("<html><body>");
        out.println("<h1>Inventory List</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>Item ID</th><th>Item Name</th><th>Quantity</th><th>Expiration Date</th><th>Status</th></tr>");
        for (InventoryItem item : items) {
            out.println("<tr>");
            out.println("<td>" + item.getItemId() + "</td>");
            out.println("<td>" + item.getItemName() + "</td>");
            out.println("<td>" + item.getQuantity() + "</td>");
            out.println("<td>" + item.getExpirationDate() + "</td>");
            out.println("<td>" + (item.isSurplus() ? "Surplus" : "Normal") + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("<a href='retailerDashboard.html'>Go back to Dashboard</a>");
        out.println("</body></html>");
    }
}
