package rs.raf.sk.Subscriber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import rs.raf.sk.Subscriber.domain.dto.MessageDto;

import javax.validation.constraints.NotNull;

@Component
@EnableBinding(OutputChannel.class)
@RequiredArgsConstructor
public class Publisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

    public void produceMsg(String r, String s, String msg){

        String str = "{ \"recipient\": \"" + r + "\", \"subject\": \"" + s + "\", \"message\": \"" + msg + "\" }";
        System.out.println(str);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, KEY_NAME, str.getBytes());
    }
}
