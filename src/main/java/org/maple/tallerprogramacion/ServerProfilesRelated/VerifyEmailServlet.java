package org.maple.tallerprogramacion.ServerProfilesRelated;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

@WebServlet(name = "verifyEmailServlet", value = "/verify-email")
public class VerifyEmailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            response.sendRedirect("verify-email.jsp?message=Invalid token");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT user_id FROM EmailVerificationTokens WHERE token = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, token);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt("user_id");

                    String updateSql = "UPDATE users SET is_verified = TRUE WHERE id = ?";
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                        updatePstmt.setInt(1, userId);
                        updatePstmt.executeUpdate();
                    }

                    response.sendRedirect("verify-email.jsp?message=Email verified successfully");
                } else {
                    response.sendRedirect("verify-email.jsp?message=Invalid token");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("verify-email.jsp?message=An error occurred. Please try again.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}