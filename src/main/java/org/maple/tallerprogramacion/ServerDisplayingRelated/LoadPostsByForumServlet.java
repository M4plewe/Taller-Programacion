package org.maple.tallerprogramacion.ServerDisplayingRelated;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Post;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/load-posts-by-forum")
public class LoadPostsByForumServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Post> posts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            int forumId = Integer.parseInt(request.getParameter("forumId")); // Obtener ID del foro
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT * FROM posts WHERE forum_id = ? ORDER BY created_at DESC";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, forumId); // Usar ID del foro
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createdAt = rs.getString("created_at");

                    Post post = new Post(id, userId, forumId, title, content, createdAt);
                    posts.add(post);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        String json = gson.toJson(posts);
        out.print(json);
        out.flush();
    }

}
