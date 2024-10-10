package org.maple.tallerprogramacion.ServerDisplayingRelated;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Post;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/forum-data")
public class ForumDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int forumId = Integer.parseInt(request.getParameter("id"));
        String forumName = "";
        String forumDescription = "";
        List<Post> posts = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Obtener información del foro
                String forumSql = "SELECT name, description FROM forums WHERE id = ?";
                pstmt = conn.prepareStatement(forumSql);
                pstmt.setInt(1, forumId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    forumName = rs.getString("name");
                    forumDescription = rs.getString("description");
                } else {
                    // Manejo si no se encuentra el foro
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                // Obtener posts del foro y el username del autor
                String postsSql = "SELECT p.id, f.name AS forum_name, p.title, p.content, p.created_at, u.username, p.user_id " +
                        "FROM posts p " +
                        "JOIN users u ON p.user_id = u.id " +
                        "JOIN forums f ON p.forum_id = f.id " +
                        "WHERE p.forum_id = ? ORDER BY p.created_at DESC";
                pstmt = conn.prepareStatement(postsSql);
                pstmt.setInt(1, forumId);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int postId = rs.getInt("id");
                    String username = rs.getString("username");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createdAt = rs.getString("created_at");
                    int userId = rs.getInt("user_id"); // Obtener userId
                    String forumNameFromPost = rs.getString("forum_name");

                    // Crear el objeto Post con el userId y forumId
                    Post post = new Post(postId, username, forumNameFromPost, title, content, createdAt, userId, forumId);
                    posts.add(post);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Manejo de error del servidor
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Configurar la respuesta
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> result = new HashMap<>();
        result.put("name", forumName);
        result.put("description", forumDescription);
        result.put("posts", posts);
        String json = gson.toJson(result);
        out.print(json);
        out.flush();
    }
}
