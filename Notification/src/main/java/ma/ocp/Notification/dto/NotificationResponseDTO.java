package ma.ocp.Notification.dto;

import java.time.LocalDateTime;

import lombok.Data;
import ma.ocp.Notification.enums.NotificationType;

@Data
public class NotificationResponseDTO {
    private String id;
    private Long userId; 
    private String title;
    private String message;
    private NotificationType type;
    private boolean isRead;
    private LocalDateTime createdAt;
}