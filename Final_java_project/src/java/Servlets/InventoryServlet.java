package Servlets;

import model.InventoryItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
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
    private static final String DB_USER = "Mishi";
    private static final String DB_PASSWORD = "Mishi@2022";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String message = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            switch (action) {
                case "add":
                    addInventoryItem(request, response);
                    break;
                case "update":
                    updateInventoryItem(request, response);
                    break;
                case "markSurplus":
                    markSurplusItem(request, response);
                    checkSurplusAndNotify();  // Send notifications to subscribed users
                    break;
                case "subscribe":
                    subscribeUser(request, response);
                    break;
                default:
                    message = "Invalid action.";
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            message = "MySQL JDBC Driver not found.";
            response.setContentType("text/plain");
            try (PrintWriter out = response.getWriter()) {
                out.write(message);
            }
        }
    }

    private void addInventoryItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemName = request.getParameter("itemName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String expirationDate = request.getParameter("expirationDate");

        String message;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO inventory (item_name, quantity, expiration_date) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, itemName);
                stmt.setInt(2, quantity);
                stmt.setString(3, expirationDate);
                int rowsAffected = stmt.executeUpdate();
                message = rowsAffected > 0 ? "Item added successfully!" : "Failed to add item.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error adding item: " + e.getMessage();
        }

        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            out.write(message);
        }
    }

    private void updateInventoryItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int newQuantity = Integer.parseInt(request.getParameter("quantity"));

        String message;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE inventory SET quantity = ? WHERE item_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, newQuantity);
                stmt.setInt(2, itemId);
                int rowsAffected = stmt.executeUpdate();
                message = rowsAffected > 0 ? "Item quantity updated successfully!" : "Failed to update item.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error updating item: " + e.getMessage();
        }

        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            out.write(message);
        }
    }

    private void markSurplusItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        boolean forDonation = request.getParameter("forDonation") != null;
        boolean forSale = request.getParameter("forSale") != null;
        double price = Double.parseDouble(request.getParameter("price"));

        String message;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE inventory SET is_for_donation = ?, is_for_sale = ?, price = ? WHERE item_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, forDonation);
                stmt.setBoolean(2, forSale);
                stmt.setDouble(3, price);
                stmt.setInt(4, itemId);
                int rowsAffected = stmt.executeUpdate();
                message = rowsAffected > 0 ? "Item surplus status updated successfully!" : "Failed to update item surplus status.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error updating item surplus status: " + e.getMessage();
        }

        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            out.write(message);
        }
    }

    private void subscribeUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String location = request.getParameter("location");
        String preference = request.getParameter("preference");
        String communicationMethod = request.getParameter("communicationMethod");

        String message;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO users (username, email, phone, location, preference, communication_method) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, location);
                stmt.setString(5, preference);
                stmt.setString(6, communicationMethod);
                int rowsAffected = stmt.executeUpdate();
                message = rowsAffected > 0 ? "User subscribed successfully!" : "Failed to subscribe user.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error subscribing user: " + e.getMessage();
        }

        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            out.write(message);
        }
    }

    private void checkSurplusAndNotify() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT u.username, u.email, u.phone, u.communication_method, i.item_name FROM users u "
                       + "JOIN user_subscriptions us ON u.user_id = us.user_id "
                       + "JOIN inventory i ON us.item_id = i.item_id "
                       + "WHERE i.expiration_date <= DATE_ADD(CURDATE(), INTERVAL 7 DAY)";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String communicationMethod = rs.getString("communication_method");
                    String contactInfo = communicationMethod.equals("email") ? rs.getString("email") : rs.getString("phone");
                    String itemName = rs.getString("item_name");
                    System.out.println("Notifying user (" + contactInfo + ") about surplus item: " + itemName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<InventoryItem> items;
        String view = request.getParameter("view");

        if ("surplus".equals(view)) {
            items = getSurplusItems();
        } else {
            items = getAllItems();
        }

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            out.println("[");
            for (int i = 0; i < items.size(); i++) {
                InventoryItem item = items.get(i);
                out.println("  {");
                out.println("    \"itemId\": " + item.getItemId() + ",");
                out.println("    \"itemName\": \"" + item.getItemName() + "\",");
                out.println("    \"quantity\": " + item.getQuantity() + ",");
                out.println("    \"expirationDate\": \"" + new SimpleDateFormat("yyyy-MM-dd").format(item.getExpirationDate()) + "\",");
                out.println("    \"surplus\": " + (item.isSurplus() ? "true" : "false") + ",");
                out.println("    \"forDonation\": " + (item.isForDonation() ? "true" : "false") + ",");
                out.println("    \"forSale\": " + (item.isForSale() ? "true" : "false") + ",");
                out.println("    \"price\": " + item.getPrice());
                out.print("  }" + (i < items.size() - 1 ? "," : ""));
            }
            out.println("]");
        }
    }

    private List<InventoryItem> getAllItems() {
        List<InventoryItem> items = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM inventory";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InventoryItem item = new InventoryItem();
                    item.setItemId(rs.getInt("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    Date dbDate = rs.getDate("expiration_date");
                    item.setExpirationDate(dbDate);
                    item.setSurplus(isSurplus(dbDate));
                    item.setForDonation(rs.getBoolean("is_for_donation"));
                    item.setForSale(rs.getBoolean("is_for_sale"));
                    item.setPrice(rs.getDouble("price"));
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    private List<InventoryItem> getSurplusItems() {
        List<InventoryItem> surplusItems = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM inventory WHERE expiration_date <= DATE_ADD(CURDATE(), INTERVAL 7 DAY)";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InventoryItem item = new InventoryItem();
                    item.setItemId(rs.getInt("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    Date dbDate = rs.getDate("expiration_date");
                    item.setExpirationDate(dbDate);
                    item.setSurplus(true);
                    item.setForDonation(rs.getBoolean("is_for_donation"));
                    item.setForSale(rs.getBoolean("is_for_sale"));
                    item.setPrice(rs.getDouble("price"));
                    surplusItems.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return surplusItems;
    }

    private boolean isSurplus(Date expirationDate) {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        LocalDate expiration = expirationDate.toLocalDate();
        return !expiration.isAfter(nextWeek);
    }
}
