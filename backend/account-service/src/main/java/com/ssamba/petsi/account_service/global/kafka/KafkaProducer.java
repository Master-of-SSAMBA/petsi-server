package com.ssamba.petsi.account_service.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ssamba.petsi.account_service.global.dto.NoticeProducerDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, NoticeProducerDto<NoticeProducerDto.AccountNoticeInfo>> kafkaTemplate;

	public void send(NoticeProducerDto<NoticeProducerDto.AccountNoticeInfo> noticeProducerDto) {
		kafkaTemplate.send("transfer-success", noticeProducerDto);
	}

}
