package org.maple.tallerprogramacion.ServerProfilesRelated;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

@WebServlet(name = "verifyEmailServlet", urlPatterns = {"/verifyemailservice"})
public class VerifyEmailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter("token");

        response.setContentType("text/plain");  // Asegúrate de enviar una respuesta en texto plano

        // Validar el token
        if (token == null || token.isEmpty()) {
            response.getWriter().write("Te enviamos un correo de verificación.");
            return;
        }

        try (Connection conn = new MySQLDBConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT user_id FROM EmailVerificationTokens WHERE token = ?")) {

            pstmt.setString(1, token);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");

                    // Actualizar el estado de verificación del usuario
                    try (PreparedStatement updatePstmt = conn.prepareStatement("UPDATE users SET is_verified = TRUE WHERE id = ?")) {
                        updatePstmt.setInt(1, userId);
                        int rowsUpdated = updatePstmt.executeUpdate();

                        if (rowsUpdated > 0) {
                            response.getWriter().write("Email verified successfully."
                            + "You will be redirected to the login page in 5 seconds.");
                            // redirigir a la página de inicio de sesión despues de 5 segundos
                            response.setHeader("Refresh", "5;url=/login");
                        } else {
                            response.getWriter().write("Error updating user verification.");
                        }
                    }

                } else {
                    response.getWriter().write("Invalid token");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred. Please try again.");
        }
    }
}
