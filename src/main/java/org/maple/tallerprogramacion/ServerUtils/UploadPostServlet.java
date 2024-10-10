package org.maple.tallerprogramacion.ServerUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Forum;
import java.io.IOException;

@WebServlet("/uploadpost")
public class UploadPostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el nombre del foro del parámetro de la solicitud
        String forumNombre = request.getParameter("forumName");

        if (forumNombre != null && !forumNombre.isEmpty()) {
            // Obtener la información del foro desde la base de datos
            Forum forum = Forum.getForumInfoFromDatabase(forumNombre);

            if (forum != null) {
                // Pasar la información del foro a la JSP
                request.setAttribute("forum", forum);
                request.getRequestDispatcher("/new_uploadpost.jsp").forward(request, response);
            } else {
                // Si el foro no existe, devolver un 404
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Forum not found.");
            }
        } else {
            // Si no se proporciona el nombre del foro, devolver un 400
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Forum name is required.");
        }
    }
}