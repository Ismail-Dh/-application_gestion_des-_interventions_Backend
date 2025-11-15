package ma.ocp.log_service.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE = "logs.queue";

    @Bean
    public Queue logsQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public DirectExchange logsExchange() {
        return new DirectExchange("logs.exchange");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(logsQueue())
                .to(logsExchange())
                .with("logs.routingkey");
    }
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}