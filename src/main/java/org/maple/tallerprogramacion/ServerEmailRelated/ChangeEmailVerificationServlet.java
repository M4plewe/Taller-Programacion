package org.maple.tallerprogramacion.ServerEmailRelated;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

@WebServlet(name = "ChangeEmailVerificationServlet", value = "/verifyemailservicechange")
public class ChangeEmailVerificationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            response.getWriter().write("Invalid token");
            return;
        }

        try (Connection conn = new MySQLDBConnection().getConnection()) {
            if (conn != null) {
                String selectSql = "SELECT user_id, new_email FROM emailchangeverificationtokens WHERE token = ?";
                try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
                    selectPstmt.setString(1, token);
                    try (ResultSet rs = selectPstmt.executeQuery()) {
                        if (rs.next()) {
                            int userId = rs.getInt("user_id");
                            String newEmail = rs.getString("new_email");

                            // Update the user's email
                            String updateSql = "UPDATE users SET email = ? WHERE id = ?";
                            try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                                updatePstmt.setString(1, newEmail);
                                updatePstmt.setInt(2, userId);
                                updatePstmt.executeUpdate();
                            }

                            // Delete the token after successful verification
                            String deleteSql = "DELETE FROM emailchangeverificationtokens WHERE token = ?";
                            try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                                deletePstmt.setString(1, token);
                                deletePstmt.executeUpdate();
                            }

                            response.getWriter().write("Email verified successfully");
                        } else {
                            response.getWriter().write("Invalid token");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error");
        }
    }
}