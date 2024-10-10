package org.maple.tallerprogramacion.ServerProfilesRelated;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import org.maple.tallerprogramacion.ServerConnectionTests.MySQLDBConnection;

@WebServlet(name = "recoverServlet", value = "/recover-servlet")
public class RecoverServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String password = null;

        try {
            // Obtener la conexión a la base de datos
            MySQLDBConnection dbConnection = new MySQLDBConnection();
            conn = dbConnection.getConnection();

            if (conn != null) {
                // Preparar la consulta SQL para obtener la contraseña por correo electrónico
                String sql = "SELECT password FROM Users WHERE email = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);

                // Ejecutar la consulta
                rs = pstmt.executeQuery();

                // Procesar los resultados
                if (rs.next()) {
                    password = rs.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Configurar la respuesta
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        if (password != null) {
            out.println("<h1>Su contraseña es: " + password + "</h1>");
        } else {
            out.println("<h1>Correo electrónico no registrado.</h1>");
        }

        out.println("</body></html>");
    }
}
