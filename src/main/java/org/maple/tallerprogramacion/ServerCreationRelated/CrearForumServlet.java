package org.maple.tallerprogramacion.ServerCreationRelated;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/CrearForumServlet")
public class CrearForumServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("forumName");
        String description = request.getParameter("forumDescription");
        int currentUserId = (int) request.getSession().getAttribute("currentUserId");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Check if the forum already exists
                String checkForumQuery = "SELECT id FROM forums WHERE name = ?";
                pstmt = conn.prepareStatement(checkForumQuery);
                pstmt.setString(1, name);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Forum already exists
                    response.sendRedirect("new_createforum.jsp?error=exists");
                    return;
                }
                rs.close();
                pstmt.close();

                // Insert the new forum into the database
                String insertSql = "INSERT INTO forums (user_id, name, description) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, currentUserId);
                pstmt.setString(2, name);
                pstmt.setString(3, description);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Redirect to the user menu after creating the forum
                    response.sendRedirect("http://maplehugs.ddns.net/");
                } else {
                    response.sendRedirect("new_createforum.jsp?error=creation_failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("new_createforum.jsp?error=database_error");
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