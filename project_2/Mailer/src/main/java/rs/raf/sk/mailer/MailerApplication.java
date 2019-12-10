package rs.raf.sk.mailer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rs.raf.sk.mailer.consumer.MailEventConsumer;

@SpringBootApplication
public class MailerApplication {

    private static final String EXCHANGE_NAME = "rafsk-exchange";
    private static final String QUEUE_NAME = "rafsk-queue";
    private static final String KEY_NAME = "rafsk-key";
    private static final String CONSUMER_NAME = "consumeEvent";

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

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory factory, MessageListenerAdapter adapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(adapter);

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MailEventConsumer consumer) {
        return new MessageListenerAdapter(consumer, CONSUMER_NAME);
    }

    public static void main(String[] args) {
        SpringApplication.run(MailerApplication.class, args);
    }
}