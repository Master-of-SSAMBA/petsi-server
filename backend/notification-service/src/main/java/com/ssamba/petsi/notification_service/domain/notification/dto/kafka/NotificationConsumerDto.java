package com.ssamba.petsi.notification_service.domain.notification.dto.kafka;

import com.ssamba.petsi.notification_service.domain.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class NotificationConsumerDto {
    private Long userId;
    private String category;
    private String content;
    private Long id;

    public static Notification toEntity(NotificationConsumerDto consumer) {
        return Notification.builder()
                .userId(consumer.getUserId())
                .category(consumer.getCategory())
                .content(consumer.getContent())
                .linkId(consumer.getId())
                .build();
    }

}
