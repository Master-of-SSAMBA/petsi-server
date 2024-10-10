package com.ssamba.petsi.notification_service.domain.notification.repository;

import java.util.List;
import java.util.Optional;

import com.ssamba.petsi.notification_service.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query("SELECT n FROM Notification n WHERE n.userId = :userId ORDER BY n.isRead ASC, " +
		"CASE WHEN n.isRead = false THEN n.createdAt END DESC, " +
		"CASE WHEN n.isRead = true THEN n.readAt END DESC")
	List<Notification> findAllByUserIdWithCustomOrder(@Param("userId") Long userId);

	void deleteAllByUserIdAndIsRead(Long userId, boolean isRead);

	void deleteAllByUserId(Long userId);

	int countByUserIdAndIsRead(Long userId, boolean isRead);

	Optional<Notification> findByUserIdAndNotificationId(Long userId, Long notificationId);

	void deleteByUserIdAndNotificationIdIn(Long userId, List<Long> notificationIds);

	void deleteByUserIdAndNotificationId(Long userId, Long notificationId);
}
