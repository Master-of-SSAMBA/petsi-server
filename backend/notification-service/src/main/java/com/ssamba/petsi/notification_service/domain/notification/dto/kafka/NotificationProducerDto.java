package com.ssamba.petsi.notification_service.domain.notification.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationProducerDto<T> {
    private Long userId;
    private String category;
    private String content;
    private Long id;
}
