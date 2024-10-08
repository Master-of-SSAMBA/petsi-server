package com.ssamba.petsi.notification_service.domain.notification.service;

import java.util.List;
import java.util.Optional;

import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationConsumerDto;
import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationProducerDto;
import com.ssamba.petsi.notification_service.domain.notification.dto.response.NotificationResponseDto;
import com.ssamba.petsi.notification_service.domain.notification.entity.Notification;
import com.ssamba.petsi.notification_service.domain.notification.repository.NotificationRepository;
import com.ssamba.petsi.notification_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.notification_service.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

	private final NotificationRepository notificationRepository;

	@Transactional(readOnly = true)
	public int getUnreadNotificationCount(Long userId) {
		return notificationRepository.countByUserIdAndIsRead(userId, false);
	}

	@Transactional(readOnly = true)
	public List<NotificationResponseDto> getAllNotification(Long userId) {
		return notificationRepository.findAllByUserIdWithCustomOrder(userId)
			.stream()
			.map(NotificationResponseDto::fromEntity)
			.toList();
	}

	public int deleteAllNotification(Long userId, boolean option) {
		Optional.of(option)
			.ifPresentOrElse(
				opt -> notificationRepository.deleteAllByUserIdAndIsRead(userId, option),
				() -> notificationRepository.deleteAllByUserId(userId)
			);

		return getUnreadNotificationCount(userId);
	}

	public int deleteSpecificNotification(Long userId, List<Long> notificationIds) {
		notificationRepository.deleteByUserIdAndNotificationIdIn(userId, notificationIds);
		return getUnreadNotificationCount(userId);
	}

	public int readNotification(Long userId, Long notificationId) {
		Notification notification = notificationRepository.findByUserIdAndNotificationId(userId, notificationId).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.NOTIFICATION_NOT_FOUND)
		);
		notification.setRead(true);
		return getUnreadNotificationCount(userId);
	}

	public void saveNotification(NotificationConsumerDto consumer) {
		notificationRepository.save(NotificationConsumerDto.toEntity(consumer));
	}
}
