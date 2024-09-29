package org.maple.tallerprogramacion.ServerEmailRelated;

import io.github.cdimascio.dotenv.Dotenv;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendPasswordRecoveryEmail {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String USERNAME = dotenv.get("EMAIL_USERNAME");
    private static final String APP_PASSWORD = dotenv.get("EMAIL_APP_PASSWORD");

    public void sendPasswordRecoveryEmail(String recipientEmail, String token) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Password Recovery");
            String recoveryLink = "http://26.170.27.156:8080/TestFinalOMeMato/recover-password?token=" + token;
            message.setText("Please recover your password using this link: " + recoveryLink);

            Transport.send(message);
            System.out.println("Password recovery email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}