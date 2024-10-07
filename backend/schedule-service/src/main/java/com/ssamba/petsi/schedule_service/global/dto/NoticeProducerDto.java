package com.ssamba.petsi.schedule_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeProducerDto {
	private Long userId;
	private String content;
	private Long id;
}
