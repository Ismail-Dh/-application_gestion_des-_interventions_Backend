package ma.ocp.Notification.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ma.ocp.Notification.dto.NotificationRequestDTO;
import ma.ocp.Notification.services.NotificationService;


@Component
@RequiredArgsConstructor
public class NotifConsumer {
	private final NotificationService notificationService;

    @RabbitListener(queues = "notif.queue")
    public void consommerLog(NotificationRequestDTO notif) {
    	notificationService.createNotification(notif);
    }

}
