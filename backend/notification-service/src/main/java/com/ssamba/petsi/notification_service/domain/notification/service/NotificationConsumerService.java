package com.ssamba.petsi.notification_service.domain.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationConsumerDto;
import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationProducerDto;
import com.ssamba.petsi.notification_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.notification_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumerService {

    private final FirebaseService firebaseService;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "kafka-topic")
    public void getNotification(String json) {
        NotificationConsumerDto dto;
		try {
			dto = objectMapper.readValue(json, NotificationConsumerDto.class);
		} catch (JsonProcessingException e) {
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
        notificationService.saveNotification(dto);
        firebaseService.sendPushNotification(dto);
    }
}
