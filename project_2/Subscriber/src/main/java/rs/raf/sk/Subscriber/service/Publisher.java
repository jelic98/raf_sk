package rs.raf.sk.Subscriber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import rs.raf.sk.Subscriber.domain.dto.MessageDto;

@Component
@EnableBinding(OutputChannel.class)
@RequiredArgsConstructor
public class Publisher {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final OutputChannel outputChannel;

    private static final String EXCHANGE_NAME = "rafsk-exchange";
    private static final String QUEUE_NAME = "rafsk-queue";
    private static final String KEY_NAME = "rafsk-key";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(KEY_NAME);
    }

    public void produceMsg(MessageDto msg){
        Message<String> m = MessageBuilder.withPayload(binding(queue(), exchange()).toString() + msg).build();
        outputChannel.output().send(m);
        System.out.println("Send msg = " + msg);
    }
}
