/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.classbooker.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Antares
 */
public class SendMailTLS {
    
    final String username = "classbooker14@gmail.com";
    final String password = "potato93";
    Properties props = new Properties();
    
    public SendMailTLS(){
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }
    
    public void sendMail(String to,String pass){
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
 
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("classbooker14@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Password registration.");
            message.setText("Dear ClassBooker User\n\n"
                    + "Your password is: "+pass);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
