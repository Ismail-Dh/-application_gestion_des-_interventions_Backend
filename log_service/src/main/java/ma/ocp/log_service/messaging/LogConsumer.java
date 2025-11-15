package ma.ocp.log_service.messaging;

import lombok.RequiredArgsConstructor;
import ma.ocp.log_service.dto.LogAppDTO;
import ma.ocp.log_service.services.LogAppService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogConsumer {

    private final LogAppService logAppService;

    @RabbitListener(queues = "logs.queue")
    public void consommerLog(LogAppDTO log) {
        logAppService.enregistrerLog(log);
    }
}
