package com.ssamba.petsi.schedule_service.global.kafka;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ssamba.petsi.schedule_service.domain.schedule.service.ScheduleService;
import com.ssamba.petsi.schedule_service.global.dto.PetCustomDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

	// private final KafkaTemplate<String, Object> kafkaTemplate;
	private final ScheduleService scheduleService;

	@KafkaListener(topics = "pet-name-request-topic", groupId = "pet-service-group")
	public void handlePetIdRequest(List<PetCustomDto> pets) {
		// scheduleService.setPetCustomDtoToSchedule(pets);
	}

	@KafkaListener(topics = "user-events", groupId = "notification-service")
	public void handleUserCreatedEvent(Object event) {

	}
}
