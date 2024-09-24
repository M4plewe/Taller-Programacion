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

@WebServlet("/CrearPostServlet")
public class CrearPostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int userId = Integer.parseInt(request.getParameter("userId"));
        int forumId = Integer.parseInt(request.getParameter("forumId"));

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Obtener la conexión a la base de datos
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Insertar el nuevo post en la base de datos
                String insertSql = "INSERT INTO posts (user_id, forum_id, title, content, created_at) VALUES (?, ?, ?, ?, NOW())";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, forumId);
                pstmt.setString(3, title);
                pstmt.setString(4, content);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Redirigir de nuevo al foro después de la creación del post
                    response.sendRedirect("forum.jsp?id=" + forumId);
                } else {
                    response.getWriter().println("Error al crear el post.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error de base de datos.");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
