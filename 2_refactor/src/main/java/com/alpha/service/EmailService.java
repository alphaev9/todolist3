package com.alpha.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
@Service
public class EmailService {
    @Async
    public void sendEmail(String to,String subject,String content) throws MessagingException, InterruptedException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", "smtp.163.com");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("javaeecourse@163.com", "laskin123");
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom("javaeecourse@163.com");
        message.setRecipients(Message.RecipientType.TO, to);
        message.setSubject(subject);
        message.setText(content);
        Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        Thread.sleep(200000);
        transport.close();
    }

}
