package ma.ocp.Notification.services.imp;

import lombok.RequiredArgsConstructor;
import ma.ocp.Notification.dto.NotificationRequestDTO;
import ma.ocp.Notification.dto.NotificationResponseDTO;
import ma.ocp.Notification.entities.Notification;
import ma.ocp.Notification.repositories.NotificationRepository;
import ma.ocp.Notification.services.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO request) {
        // Création d'une notification
        Notification notification = new Notification();
        notification.setUserId(request.getUserId());   // userId vient de auth-service
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        // Sauvegarde dans MongoDB
        Notification saved = notificationRepository.save(notification);
        return mapToDTO(saved);
    }

    @Override
    public List<NotificationResponseDTO> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsRead(userId, false)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void markAsRead(String notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    @Override
    public void markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndIsRead(userId, false);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    @Override
    public long countUnreadNotifications(Long userId) {
        return notificationRepository.countByUserIdAndIsRead(userId, false);
    }

    // Méthode privée pour mapper Notification -> NotificationResponseDTO
    private NotificationResponseDTO mapToDTO(Notification notification) {
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }
}


