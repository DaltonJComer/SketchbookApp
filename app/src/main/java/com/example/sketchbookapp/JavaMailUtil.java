package com.example.sketchbookapp;

import android.widget.Toast;

import com.google.android.gms.common.util.ScopeUtil;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {
    public static void sendMail(final String request, final String email) throws Exception{

        final Properties prop = new Properties();

        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");

        final String myAcc = "sketchbookPullRequests@gmail.com";
        final String pass = "fpxmtnsxxexfoodi";



        Thread t = new Thread(new Runnable() {
            @Override
            public void run()  {
                Session session = Session.getInstance(prop, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(myAcc,pass);
                    }
                });
                Message message = prepMsg(session,myAcc,request,email);

                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    System.out.println("Invalid Email");
                }
            }
        });
        t.start();
    }
    private static Message prepMsg(Session session, String myAcc, String request, final String email){
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAcc));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress("bdog5233@gmail.com"));
            message.setRecipient(Message.RecipientType.CC, new InternetAddress(email));
            message.setSubject("Pull Request");
            message.setText(request);
            return message;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static boolean isValid(String cced){
        boolean val = true;
        try{
            InternetAddress cc = new InternetAddress(cced);
            cc.validate();
        } catch (AddressException ex){
            val = false;
        }
        return val;
    }
}
