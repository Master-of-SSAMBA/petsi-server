package com.ssamba.petsi.schedule_service.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ssamba.petsi.schedule_service.global.dto.NoticeProducerDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, NoticeProducerDto<String>> kafkaTemplate;

	public void send(NoticeProducerDto<String> noticeProducerDto) {
		kafkaTemplate.send("schedule", noticeProducerDto);
	}

}
