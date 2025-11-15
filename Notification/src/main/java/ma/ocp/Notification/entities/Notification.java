package ma.ocp.Notification.entities;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import ma.ocp.Notification.enums.NotificationType;
@Document(collection = "notifications")
@Data

public class Notification {
    @Id
    private String id;
    private Long userId;
    private String title;
    private String message;
    private NotificationType type;
    private boolean isRead;
    private LocalDateTime createdAt;
}

