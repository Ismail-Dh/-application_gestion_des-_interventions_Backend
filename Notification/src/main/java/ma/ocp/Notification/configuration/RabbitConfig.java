package ma.ocp.Notification.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitConfig {
	public static final String QUEUE = "notif.queue";

    @Bean
    public Queue notifQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public DirectExchange notifExchange() {
        return new DirectExchange("notif.exchange");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(notifQueue())
                .to(notifExchange())
                .with("notif.routingkey");
    }
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
