package anjtech.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailSender {

    @Value("${spring.mail.username}")
    public String EMAIL_USER;
    @Value("${spring.mail.password}")
    public String EMAIL_PASSWORD;
    @Value("${spring.mail.host}")
    public String EMAIL_HOST;

    public void sendEmail(String send_from, String send_to,
                          String send_head, String send_content) throws MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.host", EMAIL_HOST);

        Session session = Session.getInstance(prop);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(send_from));
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(send_to));

        msg.setSubject(send_head);
        msg.setContent(send_content, "text/html;charset=UTF-8");

        Transport transport = session.getTransport();
        transport.connect(EMAIL_USER, EMAIL_PASSWORD);

        transport.sendMessage(msg, msg.getAllRecipients());

        transport.close();
    }
}
