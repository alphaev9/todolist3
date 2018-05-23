package com.alpha.worker;

import com.alpha.entity.Cooperator;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailTask implements Runnable {
    private String from = "javaeecourse@163.com";
    private String to;
    private String SMTPHost;
    private String subject;
    private String text;

    public EmailTask(Cooperator to, String SMTPHost) {
        this.to = to.getEmail();
        this.SMTPHost = SMTPHost;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        try {
            sendEmail();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendEmail() throws MessagingException, InterruptedException {
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
        message.setSubject(subject);
        message.setText(text);
        Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        Thread.sleep(200000);
        transport.close();
    }
}
