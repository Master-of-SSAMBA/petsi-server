package com.ssamba.petsi.picture_service.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ssamba.petsi.picture_service.global.dto.NotificationProducerDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, NotificationProducerDto<Long>> kafkaTemplate;

	public void send(NotificationProducerDto<Long> notificationProducerDto) {
		kafkaTemplate.send("upload-picture", notificationProducerDto);
	}

}
