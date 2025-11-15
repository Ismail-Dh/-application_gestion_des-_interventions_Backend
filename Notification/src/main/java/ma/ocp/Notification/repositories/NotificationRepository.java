package ma.ocp.Notification.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import ma.ocp.Notification.entities.Notification;


@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
	List<Notification> findByUserId(Long userId);
	  List<Notification> findByUserIdAndIsRead(Long userId, boolean isRead);
	  long countByUserIdAndIsRead(Long userId, boolean isRead);
}