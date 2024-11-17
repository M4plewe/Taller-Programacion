package org.maple.tallerprogramacion.ServerProfilesRelated;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

import java.io.IOException;
import java.sql.*;

@WebServlet("/subscription")
public class SubscriptionUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String forumId = request.getParameter("forumId");
        Integer userId = (Integer) request.getSession().getAttribute("currentUserId");

        if (userId == null || forumId == null || action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        Connection conn = null;
        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            String sql;
            if ("follow".equals(action)) {
                sql = "INSERT INTO subscription (user_id, forum_id) VALUES (?, ?)";
            } else if ("unfollow".equals(action)) {
                sql = "DELETE FROM subscription WHERE user_id = ? AND forum_id = ?";
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
            }

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.setString(2, forumId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        response.sendRedirect(request.getHeader("Referer"));
    }

    public static String getSubscriptionStatus(int userId, String forumId) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT id FROM subscription WHERE user_id = ? AND forum_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, userId);
                pstmt.setString(2, forumId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    return "following";
                } else {
                    return "not following";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "error";
    }
}