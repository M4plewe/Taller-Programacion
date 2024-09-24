package org.maple.tallerprogramacion.ServerEmailRelated;

import java.io.IOException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import org.maple.tallerprogramacion.ServerUtils.PasswordRecovery;

@WebServlet(name = "passwordRecoveryServlet", value = "/sendPasswordRecoveryEmail")
public class PasswordRecoveryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");

        PasswordRecovery passwordRecovery = new PasswordRecovery();
        passwordRecovery.recoverPassword(email);

        response.sendRedirect("recover.jsp?message=Password recovery email sent. Please check your inbox.");
    }
}