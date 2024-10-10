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
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String sql = "SELECT id, username, picture, is_verified, email, gender FROM users WHERE email = ? AND password = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, password);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    boolean isVerified = rs.getBoolean("is_verified");

                    if (!isVerified) {
                        UserVerificationManager verificationManager = new UserVerificationManager();
                        verificationManager.createOrUpdateToken(userId, rs.getString("email"));
                        response.sendRedirect("new_verifyemail.jsp?message=Please verify your email to continue. A new verification email has been sent.");
                        return;
                    }

                    String profileImage = rs.getString("picture");
                    String gender = rs.getString("gender");

                    HttpSession session = request.getSession();
                    session.setAttribute("currentUserId", userId);
                    session.setAttribute("currentUsername", rs.getString("username"));
                    session.setAttribute("currentEmail", email);
                    session.setAttribute("currentProfileImage", profileImage);
                    session.setAttribute("currentGender", gender);

                    response.sendRedirect("http://maplehugs.ddns.net/");
                } else {
                    response.sendRedirect("new_login.jsp?error=Invalid email or password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("new_login.jsp?error=An error occurred. Please try again.");
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