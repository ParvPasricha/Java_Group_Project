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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/charitableOrganizationDashboard")
public class CharitableOrganizationDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fwrp";
    private static final String DB_USER = "Mishi";
    private static final String DB_PASSWORD = "Mishi@2022";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            out.println(getDonationItems());
        }
    }

    private String getDonationItems() {
        StringBuilder itemsJson = new StringBuilder();
        itemsJson.append("[");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT item_id, item_name, quantity, expiration_date FROM inventory WHERE is_for_donation = 1";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        itemsJson.append(",");
                    }
                    first = false;

                    int itemId = rs.getInt("item_id");
                    String itemName = rs.getString("item_name");
                    int quantity = rs.getInt("quantity");
                    String expirationDate = rs.getString("expiration_date");

                    itemsJson.append("{")
                            .append("\"itemId\":").append(itemId).append(",")
                            .append("\"itemName\":\"").append(itemName).append("\",")
                            .append("\"quantity\":").append(quantity).append(",")
                            .append("\"expirationDate\":\"").append(expirationDate).append("\"")
                            .append("}");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        itemsJson.append("]");
        return itemsJson.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("claimItem".equals(action)) {
            claimItem(request, response);
        }
    }

    private void claimItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        String message;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Check if enough quantity is available
            String checkSql = "SELECT quantity FROM inventory WHERE item_id = ? AND is_for_donation = 1";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, itemId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        int availableQuantity = rs.getInt("quantity");
                        if (availableQuantity >= quantity) {
                            // Update inventory quantity
                            String updateSql = "UPDATE inventory SET quantity = quantity - ? WHERE item_id = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                                updateStmt.setInt(1, quantity);
                                updateStmt.setInt(2, itemId);
                                int rowsUpdated = updateStmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    // Insert into claims table
                                    String insertSql = "INSERT INTO claims (user_id, item_id, claim_type, quantity) VALUES (?, ?, 'charitable', ?)";
                                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                                        insertStmt.setInt(1, userId);
                                        insertStmt.setInt(2, itemId);
                                        insertStmt.setInt(3, quantity);
                                        int rowsInserted = insertStmt.executeUpdate();
                                        message = rowsInserted > 0 ? "Item claimed successfully!" : "Failed to claim item.";
                                    }
                                } else {
                                    message = "Failed to update inventory.";
                                }
                            }
                        } else {
                            message = "Requested quantity not available.";
                        }
                    } else {
                        message = "Item not found or not available for donation.";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error claiming item: " + e.getMessage();
        }

        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            out.write(message);
        }
    }
}
