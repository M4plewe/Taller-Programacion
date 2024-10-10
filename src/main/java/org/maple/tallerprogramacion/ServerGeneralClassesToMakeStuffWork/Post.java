package org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork;

import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Post {
    private int id;
    private int userId;
    private int forumId;
    private String title;
    private String content;
    private String createdAt;
    private String username;
    private String forumName;

    // Constructor actualizado para incluir userId y forumId
    public Post(int id, String username, String forumName, String title, String content, String createdAt, int userId, int forumId) {
        this.id = id;
        this.username = username;
        this.forumName = forumName; // Corregido el nombre del parámetro
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId; // Asignación de userId
        this.forumId = forumId; // Asignación de forumId
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getForumId() {
        return forumId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username; // Agregado getter para username
    }

    public String getForumName() {
        return forumName; // Agregado getter para forumName
    }

    public static Post getPostInfoFromDatabase(String postTitle) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Post post = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT p.id, p.user_id, p.forum_id, p.title, p.content, p.created_at, u.username, f.name AS forum_name " +
                        "FROM posts p " +
                        "JOIN users u ON p.user_id = u.id " +
                        "JOIN forums f ON p.forum_id = f.id " +
                        "WHERE p.title = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, postTitle);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    int forumId = rs.getInt("forum_id");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createdAt = rs.getString("created_at");
                    String username = rs.getString("username");
                    String forumName = rs.getString("forum_name");

                    post = new Post(id, username, forumName, title, content, createdAt, userId, forumId);
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

        return post;
    }
}
