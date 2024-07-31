<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Consumer Dashboard</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Consumer Dashboard</h1>
    <h2>Available Surplus Items</h2>
    <!-- Display surplus items here -->
    <form action="consumer" method="post">
        <input type="hidden" name="action" value="purchaseItem">
        <label for="itemId">Item ID:</label>
        <input type="text" id="itemId" name="itemId" required><br>
        <input type="submit" value="Purchase Item">
    </form>
</body>
</html>
