package org.maple.tallerprogramacion.ServerSearchingRelated;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Forum;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Post;
import org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;

@WebServlet("/search")
public class ForumSearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchType = request.getParameter("type");
        String searchQuery = request.getParameter("q");
        String searchTime = request.getParameter("time");
        String offsetParam = request.getParameter("offset");
        String limitParam = request.getParameter("limit");

        if (searchType == null) searchType = "posts";
        if (searchTime == null) searchTime = "all";
        int offset = (offsetParam != null) ? Integer.parseInt(offsetParam) : 0;
        int limit = (limitParam != null) ? Integer.parseInt(limitParam) : 20;

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        try {
            List<?> results = null;
            if ("forums".equalsIgnoreCase(searchType)) {
                results = searchForumsByName(searchQuery, searchTime, limit, offset);
            } else if ("posts".equalsIgnoreCase(searchType)) {
                results = searchPostsByContentOrTitle(searchQuery, searchTime, limit, offset);
            } else if ("users".equalsIgnoreCase(searchType)) {
                results = searchUsersByUsername(searchQuery, searchTime, limit, offset);
            }

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("type", searchType);
            responseMap.put("results", results);

            String json = gson.toJson(responseMap);
            out.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }

    private List<Forum> searchForumsByName(String searchQuery, String searchTime, int limit, int offset) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Forum> forums = new ArrayList<>();

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT f.id, f.user_id, f.name, f.description, f.created_at, COUNT(p.id) AS post_count, u.username AS creator_username " +
                        "FROM forums f " +
                        "LEFT JOIN posts p ON f.id = p.forum_id " +
                        "JOIN users u ON f.user_id = u.id " +
                        "WHERE f.name LIKE ? " +
                        "AND f.created_at >= ? " +
                        "GROUP BY f.id, f.user_id, f.name, f.description, f.created_at, u.username " +
                        "LIMIT ? OFFSET ?";

                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchQuery + "%");
                pstmt.setString(2, getTimeFilterDate(searchTime));
                pstmt.setInt(3, limit);
                pstmt.setInt(4, offset);

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String createdAt = rs.getString("created_at");
                    int postCount = rs.getInt("post_count");
                    String creatorUsername = rs.getString("creator_username");

                    forums.add(new Forum(id, userId, name, description, createdAt, postCount, creatorUsername));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return forums;
    }

    private String getTimeFilterDate(String searchTime) {
        LocalDate now = LocalDate.now();
        switch (searchTime) {
            case "today":
                return now.toString();
            case "week":
                return now.minusWeeks(1).toString();
            case "month":
                return now.minusMonths(1).toString();
            default:
                return "1970-01-01"; // Default to all time
        }
    }

    private List<Post> searchPostsByContentOrTitle(String searchQuery, String searchTime, int limit, int offset) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Post> posts = new ArrayList<>();

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT p.id, f.name AS forum_name, p.title, p.content, p.created_at, u.username, p.user_id, p.forum_id " +
                        "FROM posts p " +
                        "JOIN users u ON p.user_id = u.id " +
                        "JOIN forums f ON p.forum_id = f.id " +
                        "WHERE (p.title LIKE ? OR p.content LIKE ?)";

                if ("today".equals(searchTime)) {
                    sql += " AND DATE(p.created_at) = CURDATE()";
                } else if ("week".equals(searchTime)) {
                    sql += " AND p.created_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)";
                } else if ("month".equals(searchTime)) {
                    sql += " AND p.created_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH)";
                }
                sql += " ORDER BY p.created_at DESC LIMIT ? OFFSET ?";

                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchQuery + "%");
                pstmt.setString(2, "%" + searchQuery + "%");
                pstmt.setInt(3, limit);
                pstmt.setInt(4, offset);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String forumName = rs.getString("forum_name");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createdAt = rs.getString("created_at");
                    String username = rs.getString("username");
                    int userId = rs.getInt("user_id");
                    int forumId = rs.getInt("forum_id");

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

        return posts;
    }

    private List<User> searchUsersByUsername(String searchQuery, String searchTime, int limit, int offset) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT id, username, email, gender, picture, created_at " +
                        "FROM Users " +
                        "WHERE username LIKE ? " +
                        "ORDER BY created_at DESC " +
                        "LIMIT ? OFFSET ?";

                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchQuery + "%");
                pstmt.setInt(2, limit);
                pstmt.setInt(3, offset);

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String avatar = rs.getString("picture");
                    String bio = rs.getString("gender");
                    Timestamp registrationDate = rs.getTimestamp("created_at");

                    User user = new User(id, username, email, avatar, bio, registrationDate);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }
}
