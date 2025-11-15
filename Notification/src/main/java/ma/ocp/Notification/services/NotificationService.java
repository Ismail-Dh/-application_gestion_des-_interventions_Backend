package ma.ocp.Notification.services;


import java.util.List;

import ma.ocp.Notification.dto.NotificationRequestDTO;
import ma.ocp.Notification.dto.NotificationResponseDTO;

public interface NotificationService {
    NotificationResponseDTO createNotification(NotificationRequestDTO request);
    List<NotificationResponseDTO> getUserNotifications(Long userId);
    List<NotificationResponseDTO> getUnreadNotifications(Long userId);
    void markAsRead(String notificationId);
    void markAllAsRead(Long userId);
    long countUnreadNotifications(Long userId);
}