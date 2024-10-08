package com.ssamba.petsi.notification_service.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationConsumerDto;
import com.ssamba.petsi.notification_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.notification_service.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseService {

    private final TokenService tokenService;

    public void sendPushNotification(NotificationConsumerDto consumer) {
        tokenService.getUserTokensByUserId(consumer.getUserId()).forEach(token -> {
            Message message = Message.builder()
                .setToken(token)
                .putData("title", "Petsi")
                .putData("category", consumer.getCategory())
                .putData("body", consumer.getContent())
                .putData("userId", String.valueOf(consumer.getUserId()))
                .putData("id", String.valueOf(consumer.getId()))
                .build();
            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                throw new BusinessLogicException(ExceptionCode.FIREBASE_SEND_ERROR);
            }
        });
    }
}
