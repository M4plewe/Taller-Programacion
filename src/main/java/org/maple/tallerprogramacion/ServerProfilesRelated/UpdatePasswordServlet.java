package org.maple.tallerprogramacion.ServerProfilesRelated;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

@WebServlet(name = "UpdatePasswordServlet", value = "/UpdatePasswordServlet")
public class UpdatePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        HttpSession session = request.getSession();
        int currentUserId = (int) session.getAttribute("currentUserId");

        if (newPassword == null || newPassword.length() <= 8) {
            response.sendRedirect("new_settings.jsp?error=New password must be longer than 8 characters");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Validate current password
                String validateSql = "SELECT id FROM users WHERE id = ? AND password = ?";
                pstmt = conn.prepareStatement(validateSql);
                pstmt.setInt(1, currentUserId);
                pstmt.setString(2, currentPassword);
                rs = pstmt.executeQuery();

                if (!rs.next()) {
                    // Current password is incorrect
                    response.sendRedirect("new_settings.jsp?error=Current password is incorrect");
                    return;
                }
                rs.close();
                pstmt.close();

                // Update the password
                String updateSql = "UPDATE users SET password = ? WHERE id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setString(1, newPassword);
                pstmt.setInt(2, currentUserId);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect("new_settings.jsp?message=Password updated successfully");
                } else {
                    response.sendRedirect("new_settings.jsp?error=Error updating password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("new_settings.jsp?error=Database error");
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