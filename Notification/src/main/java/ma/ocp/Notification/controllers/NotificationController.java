package ma.ocp.Notification.controllers;

import lombok.RequiredArgsConstructor;
import ma.ocp.Notification.dto.NotificationRequestDTO;
import ma.ocp.Notification.dto.NotificationResponseDTO;
import ma.ocp.Notification.services.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponseDTO create(@RequestBody NotificationRequestDTO request) {
        return notificationService.createNotification(request);
    }

    @GetMapping("/user/{userId}")
    public List<NotificationResponseDTO> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @GetMapping("/user/{userId}/unread")
    public List<NotificationResponseDTO> getUnreadNotifications(@PathVariable Long userId) {
        return notificationService.getUnreadNotifications(userId);
    }

    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable String id) {
        notificationService.markAsRead(id);
    }

    @PutMapping("/user/{userId}/read-all")
    public void markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
    }

    @GetMapping("/user/{userId}/count-unread")
    public long countUnread(@PathVariable Long userId) {
        return notificationService.countUnreadNotifications(userId);
    }
}

