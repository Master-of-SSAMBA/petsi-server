package com.ssamba.petsi.schedule_service.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendScheduleNotification(Object dto) {
		kafkaTemplate.send("schedule-notice-topic", dto);
	}

	public void sendPetServiceFindAll(Long userId) {
		kafkaTemplate.send("pet-service-find-all-topic", userId);
	}
}