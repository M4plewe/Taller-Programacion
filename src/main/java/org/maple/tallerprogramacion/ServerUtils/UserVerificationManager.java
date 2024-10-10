package org.maple.tallerprogramacion.ServerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;
import org.maple.tallerprogramacion.ServerEmailRelated.SendVerificationEmail;

public class UserVerificationManager {

    public boolean hasUnusedToken(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM emailverificationtokens WHERE user_id = ?";
        try (Connection conn = new MySQLDBConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void createOrUpdateToken(int userId, String email) throws SQLException {
        TokenGenerator tokenGenerator = new TokenGenerator();
        String token = tokenGenerator.generateUUIDToken();

        String query;
        boolean isUpdate = hasUnusedToken(userId);
        if (isUpdate) {
            query = "UPDATE emailverificationtokens SET token = ? WHERE user_id = ?";
        } else {
            query = "INSERT INTO emailverificationtokens (user_id, token) VALUES (?, ?)";
        }

        try (Connection conn = new MySQLDBConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            if (isUpdate) {
                // For UPDATE: SET token = ? WHERE user_id = ?
                pstmt.setString(1, token);    // token
                pstmt.setInt(2, userId);      // user_id
            } else {
                // For INSERT: VALUES (?, ?)
                pstmt.setInt(1, userId);      // user_id
                pstmt.setString(2, token);    // token
            }
            pstmt.executeUpdate();
        }

        SendVerificationEmail emailSender = new SendVerificationEmail();
        emailSender.sendVerificationEmail(email, token);
    }

}