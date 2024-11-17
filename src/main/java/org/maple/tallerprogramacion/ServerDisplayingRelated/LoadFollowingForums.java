package org.maple.tallerprogramacion.ServerDisplayingRelated;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/load-following-forums")
public class LoadFollowingForums extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("currentUserId");

        if (userId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not logged in");
            return;
        }

        List<String> forums = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            String sql = "SELECT f.name FROM forums f " +
                    "JOIN subscription s ON f.id = s.forum_id " +
                    "WHERE s.user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                forums.add(rs.getString("name"));
            }

            response.setContentType("application/json");
            response.getWriter().write(new com.google.gson.Gson().toJson(forums));
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}