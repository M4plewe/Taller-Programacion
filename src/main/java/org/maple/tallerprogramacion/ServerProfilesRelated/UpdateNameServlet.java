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

@WebServlet(name = "UpdateNameServlet", value = "/update-name-servlet")
public class UpdateNameServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newUsername = request.getParameter("newName");
        HttpSession session = request.getSession();
        int currentUserId = (int) session.getAttribute("currentUserId");

        if (newUsername == null || newUsername.trim().isEmpty()) {
            response.sendRedirect("new_settings.jsp?error=Username cannot be empty");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Check if the new username already exists
                String checkSql = "SELECT id FROM users WHERE username = ?";
                pstmt = conn.prepareStatement(checkSql);
                pstmt.setString(1, newUsername);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Username already exists
                    response.sendRedirect("new_settings.jsp?error=Username already taken");
                    return;
                }
                rs.close();
                pstmt.close();

                // Update the username
                String updateSql = "UPDATE users SET username = ? WHERE id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setString(1, newUsername);
                pstmt.setInt(2, currentUserId);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    session.setAttribute("currentUsername", newUsername);
                    response.sendRedirect("new_settings.jsp?message=Username updated successfully");
                } else {
                    response.sendRedirect("new_settings.jsp?error=Error updating username");
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