package rs.raf.sk.Subscriber.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
public interface OutputChannel {

    String CHANNEL_NAME = "raf-channel";

    @Output(CHANNEL_NAME)
    MessageChannel output();

}
