package com.ssamba.petsi.schedule_service.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ssamba.petsi.schedule_service.global.dto.NoticeProducerDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, NoticeProducerDto> kafkaTemplate;

	public void send(NoticeProducerDto noticeProducerDto) {
		kafkaTemplate.send("SCHEDULE", noticeProducerDto);
	}

}
