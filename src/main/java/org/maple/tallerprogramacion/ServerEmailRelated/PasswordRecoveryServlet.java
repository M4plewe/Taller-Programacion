package org.maple.tallerprogramacion.ServerEmailRelated;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import org.maple.tallerprogramacion.ServerEmailRelated.SendPasswordRecoveryEmail;

@WebServlet(name = "passwordRecoveryServlet", value = "/password-recovery-servlet")
public class PasswordRecoveryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String token = generateToken(); // Implement token generation logic

        SendPasswordRecoveryEmail emailSender = new SendPasswordRecoveryEmail();
        emailSender.sendPasswordRecoveryEmail(email, token);

        response.sendRedirect("password-recovery.jsp?message=Password recovery email sent.");
    }

    private String generateToken() {
        // Implement token generation logic
        return "generated-token";
    }
}