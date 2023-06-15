package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public interface EmailBO extends SuperBO {
    public void sendMail(String sender , String senderPassword  , String receiver , String messageTxt) throws MessagingException;

    public Message prepareMessage(Session session, String sender, String receiver, String messageTxt) throws MessagingException;

}
