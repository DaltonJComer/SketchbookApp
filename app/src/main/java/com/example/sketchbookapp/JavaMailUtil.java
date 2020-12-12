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

/**
 * this class is used to sent java mail which is a api built into java which can be used to send mail using gmail and other emailing services.
 * However, you have to enable a usable by unsecure apps to be able to use it properly.
 * the password given is the one that you will be given when you sign up for that. If you do, this password can be used by other people to send mail like this, but requires two factor authentication.
 * However, this cannot be used to login to your actual email.
 */
public class JavaMailUtil {
    /**
     *
     * @param request this is the message that you want to sent
     * @param email this is the email address that you want to sent it to
     * @throws Exception if any error occurs while sending email, it would not go through but throws this expection.
     */
    public static void sendMail(final String request, final String email) throws Exception{

        final Properties prop = new Properties();

        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");

        final String myAcc = "sketchbookPullRequests@gmail.com";//to addresss
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
            message.setRecipient(Message.RecipientType.TO,new InternetAddress("bdog5233@gmail.com"));//this is the to address
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
