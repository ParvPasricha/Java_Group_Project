package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/consumerDashboard")
public class ConsumerDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null || !"consumer".equals(session.getAttribute("userType"))) {
            response.sendRedirect("login.html");
            return;
        }

        response.getWriter().println("<h1>Welcome to the Consumer Dashboard</h1>");
        // Additional dashboard content here
    }
}
