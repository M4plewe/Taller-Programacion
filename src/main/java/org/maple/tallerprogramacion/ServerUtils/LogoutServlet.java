package org.maple.tallerprogramacion.ServerUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Forum;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.net.URLDecoder;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate the session
        request.getSession().invalidate();
        // Redirect to the login page or home page
        response.sendRedirect(request.getContextPath() + "/new_login");
    }
}