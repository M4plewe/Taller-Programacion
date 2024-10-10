package org.maple.tallerprogramacion.ServerEmailRelated;

import io.github.cdimascio.dotenv.Dotenv;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendPasswordRecoveryEmail {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String USERNAME = dotenv.get("GMAIL_USERNAME");
    private static final String APP_PASSWORD = dotenv.get("GMAIL_APP_PASSWORD");

    public void sendPasswordRecoveryEmail(String recipientEmail, String newPassword) {
        if (recipientEmail == null || recipientEmail.isEmpty()) {
            throw new IllegalArgumentException("Recipient email cannot be null or empty");
        }

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
            // Create a new MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Password Recovery");

            // HTML email content
            String htmlContent = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "  <style>"
                    + "    body { font-family: Arial, sans-serif; }"
                    + "    .email-container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; }"
                    + "    .header { background-color: #f7f7f7; padding: 10px; text-align: center; }"
                    + "    .content { padding: 20px; }"
                    + "    .content p { font-size: 16px; }"
                    + "    .footer { margin-top: 20px; font-size: 12px; color: #999; text-align: center; }"
                    + "  </style>"
                    + "</head>"
                    + "<body>"
                    + "  <div class='email-container'>"
                    + "    <div class='header'>"
                    + "      <h2>Password Recovery</h2>"
                    + "    </div>"
                    + "    <div class='content'>"
                    + "      <p>Dear User,</p>"
                    + "      <p>Your password has been reset. Your new password is:</p>"
                    + "      <p><strong>" + newPassword + "</strong></p>"
                    + "      <p>Please use this new password to log in and change it immediately.</p>"
                    + "    </div>"
                    + "    <div class='footer'>"
                    + "      <p>Best regards,<br>The Team</p>"
                    + "    </div>"
                    + "  </div>"
                    + "</body>"
                    + "</html>";

            // Create MimeBodyPart for HTML content
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, "text/html");

            // Create Multipart to hold the message parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Set the content of the message
            message.setContent(multipart);

            // Send the email
            Transport.send(message);
            System.out.println("Password recovery email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}