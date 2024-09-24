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
import org.maple.tallerprogramacion.ServerUtils.UserVerificationManager;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT id, username, picture, is_verified, email FROM users WHERE username = ? AND password = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    boolean isVerified = rs.getBoolean("is_verified");

                    if (!isVerified) {
                        UserVerificationManager verificationManager = new UserVerificationManager();
                        verificationManager.createOrUpdateToken(userId, rs.getString("email"));
                        response.sendRedirect("verify-email.jsp?message=Please verify your email to continue. A new verification email has been sent.");
                        return;
                    }

                    String profileImage = rs.getString("picture");

                    HttpSession session = request.getSession();
                    session.setAttribute("userId", userId);
                    session.setAttribute("username", username);
                    session.setAttribute("email", rs.getString("email"));
                    session.setAttribute("profileImage", profileImage);

                    response.sendRedirect("menuusuarios.jsp");
                } else {
                    response.sendRedirect("login.jsp?error=Invalid username or password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=An error occurred. Please try again.");
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