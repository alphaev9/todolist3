package com.alpha.service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private String from="javaeecourse@163.com";
    private String to;
    private String SMTPHost;

    public EmailSender(String to, String SMTPhost) {
        this.to = to;
        this.SMTPHost = SMTPhost;
    }

    public void sendEmail() throws MessagingException, InterruptedException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", SMTPHost);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("javaeecourse@163.com", "laskin123");
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(from);
        message.setRecipients(Message.RecipientType.TO, to);
        message.setSubject("task completed");
        message.setText("sir,the task is completed");
        Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        Thread.sleep(200000);
        transport.close();
    }
}
