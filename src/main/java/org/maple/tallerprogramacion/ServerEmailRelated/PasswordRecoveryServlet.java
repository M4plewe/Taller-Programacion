package org.maple.tallerprogramacion.ServerEmailRelated;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import org.maple.tallerprogramacion.ServerUtils.PasswordRecovery;

@WebServlet(name = "passwordRecoveryServlet", value = "/password-recovery-servlet")
public class PasswordRecoveryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            response.sendRedirect("password-recovery.jsp?message=Invalid email address.");
            return;
        }

        PasswordRecovery passwordRecovery = new PasswordRecovery();

        try {
            passwordRecovery.recoverPassword(email);
            response.sendRedirect("new_forgotpassword.jsp?message=Password recovery email sent.");
        } catch (Exception e) {
            response.sendRedirect("new_forgotpassword.jsp?message=Failed to send recovery email.");
        }
    }
}