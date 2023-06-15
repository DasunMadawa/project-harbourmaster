package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.EmailBO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailBOImpl implements EmailBO {

    @Override
    public void sendMail(String sender , String senderPassword  , String receiver , String messageTxt) throws MessagingException {
        Properties properties = new Properties();

        properties.setProperty("mail.smtp.auth" , "true");
        properties.setProperty("mail.smtp.starttls.enable" , "true");
        properties.setProperty("mail.smtp.host" , "smtp.gmail.com");
        properties.setProperty("mail.smtp.port" , "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender , senderPassword);
            }
        });

        Message message = prepareMessage(session , sender , receiver , messageTxt);
        Transport.send(message);

    }

    @Override
    public Message prepareMessage(Session session, String sender, String receiver, String messageTxt) throws MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipient(Message.RecipientType.TO , new InternetAddress(receiver));
        message.setSubject("Project Harbour Master Notification");
        message.setText(messageTxt);
        return message;

    }
}
