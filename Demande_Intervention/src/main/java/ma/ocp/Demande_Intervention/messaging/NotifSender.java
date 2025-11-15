package ma.ocp.Demande_Intervention.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ma.ocp.Notification.dto.NotificationRequestDTO;
@Component
@RequiredArgsConstructor
public class NotifSender {
	private final RabbitTemplate rabbitTemplate;

    public void envoyerLog(NotificationRequestDTO notif) {
        rabbitTemplate.convertAndSend("notif.exchange", "notif.routingkey", notif);
    }
}
