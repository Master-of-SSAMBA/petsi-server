package com.ssamba.petsi.picture_service.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ssamba.petsi.picture_service.global.dto.NoticeProducerDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, NoticeProducerDto<Long>> kafkaTemplate;

	public void send(NoticeProducerDto<Long> noticeProducerDto) {
		kafkaTemplate.send("upload-picture", noticeProducerDto);
	}

}
