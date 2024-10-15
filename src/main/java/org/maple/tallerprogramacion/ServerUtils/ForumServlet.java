package org.maple.tallerprogramacion.ServerUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Forum;
import org.maple.tallerprogramacion.ServerProfilesRelated.SubscriptionUserServlet;

import java.io.IOException;
import java.net.URLDecoder;

public class ForumServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the forum name from the URL
        String requestURI = request.getRequestURI();
        String forumNombre = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        forumNombre = URLDecoder.decode(forumNombre, "UTF-8");

        // Get forum information from the database
        Forum forum = Forum.getForumInfoFromDatabase(forumNombre);

        if (forum != null) {
            // Get the current user ID from the session
            Integer userId = (Integer) request.getSession().getAttribute("currentUserId");

            if (userId != null) {
                // Get the subscription status
                String subscriptionStatus = SubscriptionUserServlet.getSubscriptionStatus(userId, String.valueOf(forum.getId()));
                request.setAttribute("subscriptionStatus", subscriptionStatus);
            } else {
                request.setAttribute("subscriptionStatus", "not following");
            }

            // Pass the forum information and subscription status to the JSP
            request.setAttribute("forum", forum);
            request.getRequestDispatcher("/new_forum.jsp").forward(request, response);
        } else {
            // If the forum does not exist, send a 404 error
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Forum not found");
        }
    }
}