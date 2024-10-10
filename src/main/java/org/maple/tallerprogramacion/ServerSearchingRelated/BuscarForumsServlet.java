package org.maple.tallerprogramacion.ServerSearchingRelated;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Forum;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/forums")
public class BuscarForumsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("search");

        List<Forum> forums = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT f.id, f.user_id, f.name, f.description, f.created_at, COUNT(p.id) AS post_count, u.username AS creator_username " +
                        "FROM forums f " +
                        "LEFT JOIN posts p ON f.id = p.forum_id " +
                        "JOIN users u ON f.user_id = u.id " +
                        "WHERE f.name LIKE ? OR f.description LIKE ? " +
                        "GROUP BY f.id, f.user_id, f.name, f.description, f.created_at, u.username";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchTerm + "%");
                pstmt.setString(2, "%" + searchTerm + "%");
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String createdAt = rs.getString("created_at");
                    int postCount = rs.getInt("post_count");
                    String creatorUsername = rs.getString("creator_username");

                    Forum forum = new Forum(id, userId, name, description, createdAt, postCount, creatorUsername);
                    forums.add(forum);
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

        Gson gson = new Gson();
        String json = gson.toJson(forums);

        // Set the JSON data as a request attribute
        request.setAttribute("forumsJson", json);

        // Forward the request to new_search.jsp
        request.getRequestDispatcher("new_search.jsp").forward(request, response);
    }
}