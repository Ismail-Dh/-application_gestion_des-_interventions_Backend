package ma.ocp.auth_service.messaging;

import lombok.RequiredArgsConstructor;
import ma.ocp.log_service.dto.LogAppDTO;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogSender {

    private final RabbitTemplate rabbitTemplate;

    public void envoyerLog(LogAppDTO log) {
        rabbitTemplate.convertAndSend("logs.exchange", "logs.routingkey", log);
    }
}
