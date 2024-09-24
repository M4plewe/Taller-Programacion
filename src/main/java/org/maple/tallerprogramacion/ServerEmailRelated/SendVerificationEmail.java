package org.maple.tallerprogramacion.ServerEmailRelated;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendVerificationEmail {

    private static final String USERNAME = "fundmeemailsystem@gmail.com";
    private static final String APP_PASSWORD = "ezqh egpp fqcz uthh";

    public void sendVerificationEmail(String recipientEmail, String token) {
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
            message.setSubject("Email Verification");
            String verificationLink = "http://26.170.27.156:8080/TestFinalOMeMato/verify-email?token=" + token;
            message.setText("Please verify your email using this link: " + verificationLink);

            Transport.send(message);
            System.out.println("Verification email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}