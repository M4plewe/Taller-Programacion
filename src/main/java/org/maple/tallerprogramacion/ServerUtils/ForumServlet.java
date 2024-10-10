package org.maple.tallerprogramacion.ServerUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Forum;

import java.io.IOException;
import java.net.URLDecoder;

public class ForumServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el nombre de usuario de la URL
        String requestURI = request.getRequestURI(); // Ej: /forum/Nicolas%20le%20pega%20a%20las%20mujeres
        String forumNombre = requestURI.substring(requestURI.lastIndexOf("/") + 1); // Obtiene "Nicolas le pega a las mujeres"

        // Decodificar el nombre de usuario
        forumNombre = URLDecoder.decode(forumNombre, "UTF-8");

        // Conectar a la base de datos y obtener información del usuario
        Forum forum = Forum.getForumInfoFromDatabase(forumNombre);

        if (forum != null) {
            // Pasar la información a la JSP
            request.setAttribute("forum", forum);
            request.getRequestDispatcher("/new_forum.jsp").forward(request, response);

        } else {
            // Si el usuario no existe, redirigir a una página de error o 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
        }
    }
}
