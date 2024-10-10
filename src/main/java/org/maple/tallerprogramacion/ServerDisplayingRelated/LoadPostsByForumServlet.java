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
            String forumName = request.getParameter("forumName"); // Obtener nombre del foro
            int limit = Integer.parseInt(request.getParameter("limit")); // Obtener el límite de posts
            int offset = Integer.parseInt(request.getParameter("offset")); // Obtener el offset
            String timeFilter = request.getParameter("timeFilter"); // Filtro por tiempo (opcional)

            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Armar la consulta SQL según el filtro de tiempo
                StringBuilder sql = new StringBuilder("SELECT p.id, p.title, p.content, p.created_at, u.username, p.user_id, f.name AS forum_name, p.forum_id " +
                        "FROM posts p " +
                        "JOIN users u ON p.user_id = u.id " +
                        "JOIN forums f ON p.forum_id = f.id " +
                        "WHERE f.name = ? ");

                // Filtrar por el tiempo si se proporciona
                if (timeFilter != null && !timeFilter.equals("all")) {
                    switch (timeFilter) {
                        case "today":
                            sql.append("AND p.created_at >= CURDATE() ");
                            break;
                        case "week":
                            sql.append("AND p.created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) ");
                            break;
                        case "month":
                            sql.append("AND p.created_at >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) ");
                            break;
                    }
                }

                sql.append("ORDER BY p.created_at DESC LIMIT ? OFFSET ?");

                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, forumName); // Usar nombre del foro
                pstmt.setInt(2, limit); // Límite
                pstmt.setInt(3, offset); // Offset

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createdAt = rs.getString("created_at");
                    String forumNameFromDB = rs.getString("forum_name");
                    int userId = rs.getInt("user_id");
                    int forumId = rs.getInt("forum_id");

                    Post post = new Post(id, username, forumNameFromDB, title, content, createdAt, userId, forumId);
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
