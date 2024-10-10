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

@WebServlet("/CrearPostServlet")
public class CrearPostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");// Convertir a String
        String forumNombre = request.getParameter("forumName");
        int currentUserId = (int) request.getSession().getAttribute("currentUserId");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Obtain the database connection
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Get the forum_id based on the forum name
                String forumQuery = "SELECT id FROM forums WHERE name = ?";
                pstmt = conn.prepareStatement(forumQuery);
                pstmt.setString(1, forumNombre);
                rs = pstmt.executeQuery();
                int forumId = 0;
                if (rs.next()) {
                    forumId = rs.getInt("id");
                } else {
                    response.getWriter().println("Error: Forum not found.");
                    return;
                }
                rs.close();
                pstmt.close();

                // Insert the new post into the database
                String insertSql = "INSERT INTO posts (user_id, forum_id, title, content, created_at) VALUES (?, ?, ?, ?, NOW())";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, currentUserId);
                pstmt.setInt(2, forumId);
                pstmt.setString(3, title);
                pstmt.setString(4, content);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Redirect back to the forum after creating the post
                    response.sendRedirect("/forum/" + forumNombre);
                } else {
                    response.getWriter().println("Error creating the post.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error.");
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