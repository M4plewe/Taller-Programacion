package org.maple.tallerprogramacion.ServerEmailRelated;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendPasswordRecoveryEmail {

    private static final String USERNAME = "fundmeemailsystem@gmail.com";
    private static final String APP_PASSWORD = "ezqh egpp fqcz uthh";

    public void sendPasswordRecoveryEmail(String recipientEmail, String newPassword) {
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
            message.setText("Your new password is: " + newPassword);

            Transport.send(message);
            System.out.println("Password recovery email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}