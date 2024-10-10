package org.maple.tallerprogramacion.ServerSearchingRelated;

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
import java.util.List;

@WebServlet("/posts")
public class BuscarPostsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Post> posts = new ArrayList<>();
        String searchTerm = request.getParameter("search");  // Obtener el término de búsqueda
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Consulta SQL actualizada para incluir el nombre del foro y el userId
                String sql = "SELECT p.id, f.name AS forum_name, p.title, p.content, p.created_at, u.username, p.user_id, p.forum_id " +
                        "FROM posts p " +
                        "JOIN users u ON p.user_id = u.id " + // Suponiendo que el id del usuario en la tabla users es 'id'
                        "JOIN forums f ON p.forum_id = f.id " + // Unir con la tabla de foros
                        "WHERE p.content LIKE ? OR p.title LIKE ? ORDER BY p.created_at DESC";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchTerm + "%");
                pstmt.setString(2, "%" + searchTerm + "%");
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String forumName = rs.getString("forum_name");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createdAt = rs.getString("created_at");
                    int userId = rs.getInt("user_id"); // Obtener userId
                    int forumId = rs.getInt("forum_id"); // Obtener forumId

                    // Crear el objeto Post con userId y forumId
                    Post post = new Post(id, username, forumName, title, content, createdAt, userId, forumId);
                    posts.add(post);
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

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String json = gson.toJson(posts);
        out.print(json);
        out.flush();
    }
}
