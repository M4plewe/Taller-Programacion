package org.maple.tallerprogramacion.ServerDisplayingRelated;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/load-top-threads")
public class TopThreadsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Forum> topThreads = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();
            if (conn != null) {
                // Consulta para obtener foros y contar el número de posts
                String sql = "SELECT f.id, f.user_id, f.name, f.description, f.created_at, COUNT(p.id) AS post_count, u.username AS creator_username " +
                        "FROM forums f " +
                        "LEFT JOIN posts p ON f.id = p.forum_id " +
                        "JOIN users u ON f.user_id = u.id " +
                        "GROUP BY f.id, f.user_id, f.name, f.description, f.created_at, u.username " +
                        "ORDER BY post_count DESC " +
                        "LIMIT 5";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String createdAt = rs.getString("created_at");
                    int postCount = rs.getInt("post_count");
                    String creatorUsername = rs.getString("creator_username");

                    // Crear el objeto Forum con el conteo de posts y el nombre de usuario del creador
                    Forum forum = new Forum(id, userId, name, description, createdAt, postCount, creatorUsername);
                    topThreads.add(forum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Devolver el resultado en formato JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(topThreads));
        out.flush();
    }
}
