package org.maple.tallerprogramacion.ServerUpdateRelated;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

@WebServlet(name = "UpdateGenderServlet", value = "/UpdateGenderServlet")
public class UpdateGenderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newGender = request.getParameter("gender");
        HttpSession session = request.getSession();
        int currentUserId = (int) session.getAttribute("currentUserId");

        if (newGender == null || newGender.isEmpty()) {
            response.sendRedirect("new_settings.jsp?error=Gender cannot be empty");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Update the gender
                String updateSql = "UPDATE users SET gender = ? WHERE id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setString(1, newGender);
                pstmt.setInt(2, currentUserId);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect("new_settings.jsp?message=Gender updated successfully");
                } else {
                    response.sendRedirect("new_settings.jsp?error=Error updating gender");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("new_settings.jsp?error=Database error");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}