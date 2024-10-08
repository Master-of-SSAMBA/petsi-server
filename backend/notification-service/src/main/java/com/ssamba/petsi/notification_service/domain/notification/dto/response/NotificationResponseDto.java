package com.ssamba.petsi.notification_service.domain.notification.dto.response;

import com.ssamba.petsi.notification_service.domain.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponseDto {
	private Long notificationId;
	private String category;
	private String content;
	private DateResponseDto date;
	private boolean isRead;
	private Long linkId;

	public static NotificationResponseDto fromEntity(Notification notification) {
		return new NotificationResponseDto(notification.getNotificationId(), notification.getCategory(), notification.getContent(),
			new DateResponseDto(notification.getCreatedAt()), notification.isRead(), notification.getLinkId());
	}
}