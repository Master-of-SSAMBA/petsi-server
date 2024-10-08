package com.ssamba.petsi.notification_service.domain.notification.service;

import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationConsumerDto;
import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationProducerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumerService {
    private final FirebaseService firebaseService;
    private final NotificationService notificationService;

    @KafkaListener(topics = "kafka-topic")
    public void consumeUploadPictureNotification(NotificationProducerDto producer) {
        NotificationConsumerDto consumer = NotificationConsumerDto.fromProducer(producer);
        notificationService.saveNotification(consumer);
        firebaseService.sendPushNotification(consumer);
    }
}
