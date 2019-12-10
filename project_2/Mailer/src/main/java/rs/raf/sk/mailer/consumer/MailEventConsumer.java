package rs.raf.sk.mailer.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;

@Component
public class MailEventConsumer {

    @Autowired
    private JavaMailSender mailSender;

    private CountDownLatch latch = new CountDownLatch(1);

    private void sendEmail(String recipient, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(recipient);
        mail.setSubject(subject);
        mail.setText(message);

        mailSender.send(mail);
    }

    public void consumeEvent(byte[] bytes) {
        try {
            JsonNode payload = new ObjectMapper().readTree(new String(bytes));
            String recipient = payload.get("recipient").textValue();
            String subject = payload.get("subject").textValue();
            String message = payload.get("message").textValue();

            sendEmail(recipient, subject, message);

            latch.countDown();

            System.out.print("Successfully consumed: " + payload.toPrettyString());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
