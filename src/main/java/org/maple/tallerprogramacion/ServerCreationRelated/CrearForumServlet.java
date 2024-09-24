package org.maple.tallerprogramacion.ServerCreationRelated;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/CrearForumServlet")
public class CrearForumServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión y los atributos del usuario
        var session = request.getSession();
        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");
        String profileImage = (String) session.getAttribute("profileImage");
        Integer userId = (Integer) session.getAttribute("user_id");

        // Obtener la información del formulario
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Validar si el usuario está autenticado
        if (userId == null) {
            // Redirigir al login si no hay sesión
            response.sendRedirect("login.jsp");
            return;
        }

        // Crear el foro en la base de datos
        try (Connection connection = new MySQLDBConnection().getConnection()) {
            String query = "INSERT INTO Forums (user_id, name, description) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setString(3, description);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Redirigir al menú de usuario en caso de éxito
                response.sendRedirect("menuusuarios.jsp");
            } else {
                // Manejar el caso de error
                request.setAttribute("error", "No se pudo crear el foro.");
                request.getRequestDispatcher("crearforos.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error al crear el foro", e);
        }
    }
}
