package org.maple.tallerprogramacion.ServerEmailRelated;

import io.github.cdimascio.dotenv.Dotenv;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendVerificationEmail {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String USERNAME = dotenv.get("GMAIL_USERNAME");
    private static final String APP_PASSWORD = dotenv.get("GMAIL_APP_PASSWORD");

    public void sendVerificationEmail(String recipientEmail, String token) {
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
            message.setSubject("Action Required: Please Verify Your Email Address");

            // HTML email content
            String verificationLink = "http://maplehugs.ddns.net/new_verifyemail.jsp?token=" + token;
            String htmlContent = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "  <style>"
                    + "    body { font-family: Arial, sans-serif; }"
                    + "    .email-container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; }"
                    + "    .header { background-color: #f7f7f7; padding: 10px; text-align: center; }"
                    + "    .content { padding: 20px; }"
                    + "    .content p { font-size: 16px; }"
                    + "    .verify-button { display: inline-block; padding: 10px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 5px; }"
                    + "    .footer { margin-top: 20px; font-size: 12px; color: #999; text-align: center; }"
                    + "  </style>"
                    + "</head>"
                    + "<body>"
                    + "  <div class='email-container'>"
                    + "    <div class='header'>"
                    + "      <h2>Verify Your Email</h2>"
                    + "    </div>"
                    + "    <div class='content'>"
                    + "      <p>Dear User,</p>"
                    + "      <p>Thank you for registering with us. To complete your registration, please verify your email address by clicking the button below:</p>"
                    + "      <p><a href='" + verificationLink + "' class='verify-button'>Verify Email</a></p>"
                    + "      <p>If you did not create an account with us, please disregard this email.</p>"
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
            System.out.println("Verification email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
