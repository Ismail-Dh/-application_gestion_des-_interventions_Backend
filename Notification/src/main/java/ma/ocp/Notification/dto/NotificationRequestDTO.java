package ma.ocp.Notification.dto;

import lombok.Data;
import ma.ocp.Notification.enums.NotificationType;

@Data
public class NotificationRequestDTO {
	 private Long userId; 
    private String title;
    private String message;
    private NotificationType type;
}