package com.ssamba.petsi.account_service.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssamba.petsi.account_service.global.dto.NotificationProducerDto;
import com.ssamba.petsi.account_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.account_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public void send(NotificationProducerDto notificationProducerDto) {
		try {
			String json = objectMapper.writeValueAsString(notificationProducerDto);
			kafkaTemplate.send("kafka-topic", json);
		} catch (JsonProcessingException e) {
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}

	}

}
