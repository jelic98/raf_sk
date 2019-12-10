package rs.raf.sk.mailer.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import rs.raf.sk.mailer.dto.MailDto;
import java.util.concurrent.CountDownLatch;

@Component
public class MailEventConsumer {

    @Autowired
    private JavaMailSender mailSender;

    private CountDownLatch latch = new CountDownLatch(1);

    void sendEmail(MailDto mail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail.getRecipient());

        msg.setSubject(mail.getSubject());
        msg.setText(mail.getMessage());

        mailSender.send(msg);
    }

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
