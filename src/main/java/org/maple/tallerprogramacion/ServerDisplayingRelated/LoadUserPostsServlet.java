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

@WebServlet("/load-user-posts")
public class LoadUserPostsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Post> posts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String username = request.getParameter("username");
                int limit = Integer.parseInt(request.getParameter("limit"));
                int offset = Integer.parseInt(request.getParameter("offset"));
                String timeFilter = request.getParameter("timeFilter");

                StringBuilder sql = new StringBuilder("SELECT p.id, f.name AS forum_name, p.title, p.content, p.created_at, u.username, p.user_id, p.forum_id " +
                        "FROM posts p " +
                        "JOIN users u ON p.user_id = u.id " +
                        "JOIN forums f ON p.forum_id = f.id " +
                        "WHERE u.username = ? ");

                if (timeFilter != null) {
                    switch (timeFilter) {
                        case "today":
                            sql.append("AND DATE(p.created_at) = CURDATE() ");
                            break;
                        case "week":
                            sql.append("AND p.created_at >= DATE_SUB(NOW(), INTERVAL 7 DAY) ");
                            break;
                        case "month":
                            sql.append("AND p.created_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH) ");
                            break;
                    }
                }

                sql.append("ORDER BY p.created_at DESC LIMIT ? OFFSET ?");

                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, username);
                pstmt.setInt(2, limit);
                pstmt.setInt(3, offset);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String forumName = rs.getString("forum_name");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createdAt = rs.getString("created_at");
                    String user = rs.getString("username");
                    int userId = rs.getInt("user_id");
                    int forumId = rs.getInt("forum_id");

                    Post post = new Post(id, user, forumName, title, content, createdAt, userId, forumId);
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