package com.ssamba.petsi.notification_service.domain.notification.dto.kafka;

import com.ssamba.petsi.notification_service.domain.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
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
                .isRead(false)
                .linkId(consumer.getId())
                .build();
    }

    public static NotificationConsumerDto fromProducer(NotificationProducerDto<?> producer) {
        return NotificationConsumerDto.builder()
                .userId(producer.getUserId())
                .id(producer.getId())
                .category(producer.getCategory())
                .content(producer.getContent())
                .build();
    }
}
