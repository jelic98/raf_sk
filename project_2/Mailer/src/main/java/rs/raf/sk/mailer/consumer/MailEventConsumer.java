package rs.raf.sk.mailer.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Component
public class MailEventConsumer {

    @Autowired
    private JavaMailSender mailSender;

    private CountDownLatch latch = new CountDownLatch(1);

    void sendEmail(String recipient, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(recipient);
        msg.setSubject(subject);
        msg.setText(message);

        mailSender.send(msg);
    }

    public void receiveMessage(byte[] bytes) throws IOException {
        JsonNode payload = new ObjectMapper().readTree(new String(bytes));
        String recipient = payload.get("recipient").textValue();
        String subject = payload.get("subject").textValue();
        String message = payload.get("message").textValue();

        sendEmail(recipient, subject, message);

        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
