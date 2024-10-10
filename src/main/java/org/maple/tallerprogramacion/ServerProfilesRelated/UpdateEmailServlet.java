package org.maple.tallerprogramacion.ServerEmailRelated;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

@WebServlet(name = "UpdateEmailServlet", value = "/UpdateEmailServlet")
public class UpdateEmailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int currentUserId = (int) session.getAttribute("currentUserId");
        String newEmail = request.getParameter("newEmail");

        if (newEmail == null || newEmail.isEmpty()) {
            response.sendRedirect("new_settings.jsp?error=Invalid email address");
            return;
        }

        try (Connection conn = new MySQLDBConnection().getConnection()) {
            if (conn != null) {
                // Check if the new email is already registered
                String checkEmailSql = "SELECT id FROM users WHERE email = ?";
                try (PreparedStatement checkEmailPstmt = conn.prepareStatement(checkEmailSql)) {
                    checkEmailPstmt.setString(1, newEmail);
                    try (ResultSet rs = checkEmailPstmt.executeQuery()) {
                        if (rs.next()) {
                            response.sendRedirect("new_settings.jsp?error=Email address already in use");
                            return;
                        }
                    }
                }

                // Check for existing pending verifications
                String checkSql = "SELECT id FROM emailchangeverificationtokens WHERE user_id = ? AND new_email = ?";
                try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                    checkPstmt.setInt(1, currentUserId);
                    checkPstmt.setString(2, newEmail);
                    try (ResultSet rs = checkPstmt.executeQuery()) {
                        if (rs.next()) {
                            // Delete existing pending verification
                            String deleteSql = "DELETE FROM emailchangeverificationtokens WHERE id = ?";
                            try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                                deletePstmt.setInt(1, rs.getInt("id"));
                                deletePstmt.executeUpdate();
                            }
                        }
                    }
                }

                // Insert new token
                String token = UUID.randomUUID().toString();
                String insertTokenSql = "INSERT INTO emailchangeverificationtokens (user_id, token, new_email) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertTokenSql)) {
                    pstmt.setInt(1, currentUserId);
                    pstmt.setString(2, token);
                    pstmt.setString(3, newEmail);
                    pstmt.executeUpdate();
                }

                SendChangeEmailVerification emailSender = new SendChangeEmailVerification();
                emailSender.sendChangeEmailVerification(newEmail, token);

                response.sendRedirect("new_settings.jsp?message=Verification email sent to new address");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("new_settings.jsp?error=Database error");
        }
    }
}