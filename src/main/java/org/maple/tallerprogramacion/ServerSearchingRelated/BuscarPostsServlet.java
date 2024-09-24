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
                String sql = "SELECT * FROM posts WHERE content LIKE ? OR title LIKE ? ORDER BY created_at DESC";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchTerm + "%");
                pstmt.setString(2, "%" + searchTerm + "%");
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    int forumId = rs.getInt("forum_id");
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

