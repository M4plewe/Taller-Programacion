package org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork;

import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;
import java.sql.*;


public class User {
    private int id;
    private String username;
    private String email;
    private String avatar;
    private String bio;
    private Timestamp registrationDate;

    public User(int id, String username, String email, String avatar, String bio, Timestamp registrationDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.bio = bio;
        this.registrationDate = registrationDate;
    }

    public User(int id, String username, String passwordHash, String email, String avatar, String bio, Timestamp registrationDate) {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBio() {
        return bio;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    // Método ficticio para obtener la información del usuario de la base de datos
    public static User getUserInfoFromDatabase(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT id, username, email, gender, picture, created_at FROM Users WHERE username = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String email = rs.getString("email");
                    String avatar = rs.getString("picture");
                    String bio = rs.getString("gender");
                    Timestamp registrationDate = rs.getTimestamp("created_at");

                    user = new User(id, username, email, avatar, bio, registrationDate);
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

        return user;
    }
}