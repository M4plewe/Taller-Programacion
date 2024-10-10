package org.maple.tallerprogramacion.ServerUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.User;

import java.io.IOException;
import java.net.URLDecoder;

public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el nombre de usuario de la URL
        String requestURI = request.getRequestURI(); // Ej: /user/Leico%20Sexo
        String username = requestURI.substring(requestURI.lastIndexOf("/") + 1); // Obtiene "Leico Sexo"

        // Decodificar el nombre de usuario
        username = URLDecoder.decode(username, "UTF-8");

        // Conectar a la base de datos y obtener información del usuario
        User user = User.getUserInfoFromDatabase(username);

        if (user != null) {
            // Pasar la información a la JSP
            request.setAttribute("user", user);
            request.getRequestDispatcher("/new_userprofile.jsp").forward(request, response);
        } else {
            // Si el usuario no existe, redirigir a una página de error o 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
        }
    }
}
