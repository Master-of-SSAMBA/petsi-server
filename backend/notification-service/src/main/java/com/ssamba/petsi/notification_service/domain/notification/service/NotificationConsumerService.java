package com.ssamba.petsi.notification_service.domain.notification.service;

import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationConsumerDto;
import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationProducerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.ssamba.petsi.notification_service.domain.notification.enums.Topics.*;

@Service
@RequiredArgsConstructor
public class NotificationConsumerService {
    private final FirebaseService firebaseService;
    private final NotificationService notificationService;

    @KafkaListener(topics = "upload-picture")
    public void consumeUploadPictureNotification(NotificationProducerDto<?> notificationProducerDto) {
        NotificationConsumerDto consumer = UPLOAD_PICTURE.createNotificationConsumerDto(notificationProducerDto);
        notificationService.saveNotification(consumer);
        firebaseService.sendPushNotification(consumer);
    }

    @KafkaListener(topics = "transfer-success")
    public void consumeTransferSuccessNotification(NotificationProducerDto<?> notificationProducerDto) {
        NotificationConsumerDto consumer = TRANSFER_SUCCESS.createNotificationConsumerDto(notificationProducerDto);
        notificationService.saveNotification(consumer);
        firebaseService.sendPushNotification(consumer);
    }

    @KafkaListener(topics = "schedule")
    public void consumeScheduleNotification3(NotificationProducerDto<?> notificationProducerDto) {
        NotificationConsumerDto consumer = SCHEDULE.createNotificationConsumerDto(notificationProducerDto);
        notificationService.saveNotification(consumer);
        firebaseService.sendPushNotification(consumer);
    }
}
