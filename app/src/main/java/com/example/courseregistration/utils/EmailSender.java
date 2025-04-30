package com.example.courseregistration.utils;


import android.os.StrictMode;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static void send(String toEmail, String courseName, String userName) {
        final String fromEmail = "yourapp@gmail.com"; // Replace with your Gmail
        final String password = "your_app_password";  // Use app-specific password (NOT regular password)

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); // allow sending on main thread

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Course Registration Confirmed");
            message.setText("Hi " + userName + ",\n\nYou have successfully registered for the course: " + courseName + ".\n\nThank you!");

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
