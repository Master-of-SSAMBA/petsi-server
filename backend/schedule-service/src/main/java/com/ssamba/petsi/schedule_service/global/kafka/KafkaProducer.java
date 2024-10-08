package com.ssamba.petsi.schedule_service.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ssamba.petsi.schedule_service.global.dto.NotificationProducerDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, NotificationProducerDto<NotificationProducerDto.ScheduleNoticeInfo>> kafkaTemplate;

	public void send(NotificationProducerDto<NotificationProducerDto.ScheduleNoticeInfo> notificationProducerDto) {
		kafkaTemplate.send("schedule", notificationProducerDto);
	}

}
