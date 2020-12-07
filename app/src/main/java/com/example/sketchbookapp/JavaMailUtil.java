package com.example.sketchbookapp;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {
    public static void sendMail() throws Exception{

        final Properties prop = new Properties();

        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");

        final String myAcc = "sketchbookPullRequests@gmail.com";
        final String pass = "fpxmtnsxxexfoodi";
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Session session = Session.getInstance(prop, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(myAcc,pass);
                    }
                });
                Message message = prepMsg(session,myAcc);

                try {
                    Transport.send(message);
                    System.out.println("Yay");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
    private static Message prepMsg(Session session, String myAcc){
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAcc));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress("bdog5233@gmail.com"));
            message.setSubject("Pull Request");
            message.setText("I WANT COMICS BITCH");
            return message;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
