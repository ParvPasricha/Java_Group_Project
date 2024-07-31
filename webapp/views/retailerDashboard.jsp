<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Retailer Dashboard</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Retailer Dashboard</h1>
    <form action="retailer" method="post">
        <input type="hidden" name="action" value="addItem">
        <label for="itemName">Item Name:</label>
        <input type="text" id="itemName" name="itemName" required><br>
        <label for="quantity">Quantity:</label>
        <input type="number" id="quantity" name="quantity" required><br>
        <label for="expirationDate">Expiration Date:</label>
        <input type="date" id="expirationDate" name="expirationDate" required><br>
        <input type="submit" value="Add Item">
    </form>
    <h2>Current Inventory</h2>
    <!-- Display current inventory items here -->
</body>
</html>
