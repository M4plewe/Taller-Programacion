package org.maple.tallerprogramacion.ServerUtils;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;
import org.maple.tallerprogramacion.ServerEmailRelated.SendPasswordRecoveryEmail;

public class PasswordRecovery {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 12;

    public void recoverPassword(String email) {
        String newPassword = generateRandomPassword();
        updatePasswordInDatabase(email, newPassword);

        SendPasswordRecoveryEmail emailSender = new SendPasswordRecoveryEmail();
        emailSender.sendPasswordRecoveryEmail(email, newPassword);
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    private void updatePasswordInDatabase(String email, String newPassword) {
        String updateSql = "UPDATE Users SET password = ? WHERE email = ?";
        try (Connection conn = new MySQLDBConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}