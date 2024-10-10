package org.maple.tallerprogramacion.ServerProfilesRelated;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;
import org.maple.tallerprogramacion.ServerUtils.UserVerificationManager;

@WebServlet(name = "registerServlet", value = "/register-servlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
         String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");

        // Validate password
        if (password == null || password.length() < 8) {
            response.sendRedirect("new_register.jsp?error=Password must be at least 8 characters long.");
            return;
        }

        String profileImage = "male".equals(gender) ? "profilepictures/defaultprofilepictures/defaultmaleuser.png" : "profilepictures/defaultprofilepictures/defaultfemaleuser.png";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        boolean userExists = false;

        try {
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                String checkSql = "SELECT COUNT(*) FROM Users WHERE username = ? OR email = ?";
                pstmt = conn.prepareStatement(checkSql);
                pstmt.setString(1, username);
                pstmt.setString(2, email);

                rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    userExists = true;
                }

                rs.close();
                pstmt.close();

                if (userExists) {
                    response.sendRedirect("new_register.jsp?error=Username or email already exists");
                } else {
                    String insertSql = "INSERT INTO Users (username, email, password, gender, picture, is_verified) VALUES (?, ?, ?, ?, ?, FALSE)";
                    pstmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, username);
                    pstmt.setString(2, email);
                    pstmt.setString(3, password);
                    pstmt.setString(4, gender);
                    pstmt.setString(5, profileImage);

                    pstmt.executeUpdate();

                    rs = pstmt.getGeneratedKeys();
                    int userId = 0;
                    if (rs.next()) {
                        userId = rs.getInt(1);
                    }

                    // Use UserVerificationManager to handle token generation and email sending
                    UserVerificationManager verificationManager = new UserVerificationManager();
                    verificationManager.createOrUpdateToken(userId, email);

                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    session.setAttribute("email", email);
                    session.setAttribute("profileImage", profileImage);

                    response.sendRedirect("new_verifyemail");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("new_register.jsp?error=An error occurred. Please try again.");
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