package org.maple.tallerprogramacion.ServerConnectionTests;

import io.github.cdimascio.dotenv.Dotenv;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class GmailSSLTest {

    public static void main(String[] args) {

        // Credenciales de Gmail
        Dotenv dotenv = Dotenv.load();
        final String username = dotenv.get("GMAIL_USERNAME");
        final String appPassword = dotenv.get("GMAIL_APP_PASSWORD");

        // Configuración de propiedades para la conexión SSL
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");

        // Autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, appPassword);
            }
        });

        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("fundmeemailsystem@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("mananaswithbaple@gmail.com") // Destinatario
            );
            message.setSubject("Prueba de correo con SSL");
            message.setText("Este es un mensaje de prueba enviado desde Java con SSL y javax.mail.");

            // Enviar el mensaje
            Transport.send(message);

            System.out.println("Correo enviado exitosamente!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
