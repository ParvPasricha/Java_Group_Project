<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Charitable Organization Dashboard</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Charitable Organization Dashboard</h1>
    <h2>Available Surplus Items for Donation</h2>
    <!-- Display surplus items for donation here -->
    <form action="charity" method="post">
        <input type="hidden" name="action" value="claimItem">
        <label for="itemId">Item ID:</label>
        <input type="text" id="itemId" name="itemId" required><br>
        <input type="submit" value="Claim Item">
    </form>
</body>
</html>
