package org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork;

import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;
import java.sql.*;

public class Forum {
    private int id;
    private int userId;
    private String name;
    private String description;
    private String createdAt;
    private int postCount;
    private String creatorUsername;

    public Forum(int id, int userId, String name, String description, String createdAt, int postCount, String creatorUsername) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.postCount = postCount;
        this.creatorUsername = creatorUsername;
    }

    // Getters y setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCreatedAt() { return createdAt; }
    public int getPostCount() { return postCount; }

    public static Forum getForumInfoFromDatabase(String forumName) {
        Connection conn = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Create the SQL query
                String query = "SELECT f.id, f.user_id, f.name, f.description, f.created_at, COUNT(p.id) AS post_count, u.username AS creator_username " +
                        "FROM forums f " +
                        "LEFT JOIN posts p ON f.id = p.forum_id " +
                        "JOIN users u ON f.user_id = u.id " +
                        "WHERE f.name = ? " +
                        "GROUP BY f.id, f.user_id, f.name, f.description, f.created_at, u.username";

                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, forumName);

                // Execute the query
                rs = statement.executeQuery();

                // If a result is found, create a Forum object with the information
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int userId = rs.getInt("user_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String createdAt = rs.getString("created_at");
                    int postCount = rs.getInt("post_count");
                    String creatorUsername = rs.getString("creator_username");

                    return new Forum(id, userId, name, description, createdAt, postCount, creatorUsername);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
