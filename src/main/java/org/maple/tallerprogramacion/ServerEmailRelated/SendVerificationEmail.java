package org.maple.tallerprogramacion.ServerEmailRelated;

import io.github.cdimascio.dotenv.Dotenv;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendVerificationEmail {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String USERNAME = dotenv.get("EMAIL_USERNAME");
    private static final String APP_PASSWORD = dotenv.get("EMAIL_APP_PASSWORD");

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
            message.setSubject("Action Required: Please Verify Your Email Address");

            // Improved email content
            String verificationLink = "http://26.170.27.156:8080/TestFinalOMeMato/verify-email?token=" + token;
            String emailContent = "Dear User,\n\n"
                    + "Thank you for registering with us. To complete your registration, please verify your email address "
                    + "by clicking the link below:\n\n"
                    + verificationLink + "\n\n"
                    + "If you did not create an account with us, please disregard this email.\n\n"
                    + "Best regards,\n"
                    + "The Team";

            message.setText(emailContent);

            Transport.send(message);
            System.out.println("Verification email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
