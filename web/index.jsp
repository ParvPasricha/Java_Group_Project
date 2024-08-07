<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<%
    String username = null;
    String userType = null;

    if (session != null) {
        username = (String) session.getAttribute("username");
        userType = (String) session.getAttribute("userType");
    }

    if (username != null) {
        // User is logged in, redirect based on user type
        if ("consumer".equals(userType)) {
            response.sendRedirect("consumerDashboard.html");
        } else if ("retailer".equals(userType)) {
            response.sendRedirect("retailerDashboard.html");
        } else if ("charitable".equals(userType)) {
            response.sendRedirect("charitableOrganizationDashboard.html");
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Waste Reduction Platform</title>
    <link rel="stylesheet" href="styles/base.css">
    <link rel="stylesheet" href="styles/layout.css">
    <link rel="stylesheet" href="styles/components.css">
    <link rel="stylesheet" href="styles/pages.css">
</head>
<body>
    <header class="header">
        <div class="container">
            <div class="logo">
                <a href="index.jsp">Food Waste Reduction</a>
            </div>
            <nav class="nav">
                <ul>
                    <li><a href="index.jsp">Home</a></li>
                    <% if (username == null) { %>
                        <li><a href="register.html">Register</a></li>
                        <li><a href="login.html">Login</a></li>
                    <% } else { %>
                        <li><a href="logout">Logout</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>

    <main class="hero">
        <div class="container">
            <h1>Reduce Food Waste, Share with Those in Need</h1>
            <p>Join us in making a difference by reducing food waste and sharing surplus food with others.</p>
            <% if (username == null) { %>
                <a href="register.html" class="btn btn-primary">Get Started</a>
            <% } %>
        </div>
    </main>
    
    <section class="features">
        <div class="container">
            <h2>Features</h2>
            <div class="feature-cards">
                <div class="card">
                    <% if (username == null) { %>
                        <h3><a href="login.html">For Retailers</a></h3>
                    <% } else if ("retailer".equals(userType)) { %>
                        <h3><a href="retailerDashboard.html">For Retailers</a></h3>
                    <% } %>
                    <p>Manage inventory and list surplus food items for sale or donation.</p>
                </div>
                <div class="card">
                    <% if (username == null) { %>
                        <h3><a href="login.html">For Consumers</a></h3>
                    <% } else if ("consumer".equals(userType)) { %>
                        <h3><a href="consumerDashboard.html">For Consumers</a></h3>
                    <% } %>
                    <p>Purchase surplus food items at a discounted rate.</p>
                </div>
                <div class="card">
                    <% if (username == null) { %>
                        <h3><a href="login.html">For Charitable Organizations</a></h3>
                    <% } else if ("charitable".equals(userType)) { %>
                        <h3><a href="charitableOrganizationDashboard.html">For Charitable Organizations</a></h3>
                    <% } %>
                    <p>Claim surplus food donations for your organization.</p>
                </div>
            </div>
        </div>
    </section>

    <footer class="footer">
        <div class="container">
            <p>&copy; 2024 Food Waste Reduction Platform. All rights reserved.</p>
            <ul>
                <li><a href="#">Privacy Policy</a></li>
                <li><a href="#">Terms of Service</a></li>
                <li><a href="#">Contact Us</a></li>
            </ul>
        </div>
    </footer>
</body>
</html>
