package com.ssamba.petsi.notification_service.domain.notification.service;

import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationProducerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProducerService {
    private final KafkaTemplate<String, NotificationProducerDto> kafkaTemplate;

    public void sendNotification(String topic, NotificationProducerDto noticeProducerDto) {
        kafkaTemplate.send(topic, noticeProducerDto);
    }
}
