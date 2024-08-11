package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        if (request.getSession(false) != null) {
            // Remove any user-specific session attributes
            request.getSession().removeAttribute("userType");
            request.getSession().removeAttribute("loggedIn");

            // Invalidate the session, effectively logging the user out
            request.getSession().invalidate();
        }

        // Redirect to home page after logout
        response.sendRedirect("index.jsp");
    }
}
