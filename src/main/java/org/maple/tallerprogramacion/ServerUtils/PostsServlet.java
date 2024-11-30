package org.maple.tallerprogramacion.ServerUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Post;

import java.io.IOException;

public class PostsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the post ID from the URL
        String requestURI = request.getRequestURI();
        String postIdStr = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        int postId = Integer.parseInt(postIdStr);

        // Get post information from the database
        Post post = Post.getPostInfoFromDatabase(postId);

        if (post != null) {
            // Pass the post information to the JSP
            request.setAttribute("post", post);
            request.getRequestDispatcher("/new_post.jsp").forward(request, response);
        } else {
            // If the post does not exist, send a 404 error
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
        }
    }
}