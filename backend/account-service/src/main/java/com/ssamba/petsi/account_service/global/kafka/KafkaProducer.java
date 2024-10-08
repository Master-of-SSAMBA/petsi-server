package com.ssamba.petsi.account_service.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ssamba.petsi.account_service.global.dto.NotificationProducerDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, NotificationProducerDto<NotificationProducerDto.AccountNoticeInfo>> kafkaTemplate;

	public void send(NotificationProducerDto<NotificationProducerDto.AccountNoticeInfo> notificationProducerDto) {
		kafkaTemplate.send("transfer-success", notificationProducerDto);
	}

}
