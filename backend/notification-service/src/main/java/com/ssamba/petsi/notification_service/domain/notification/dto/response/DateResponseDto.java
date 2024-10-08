package com.ssamba.petsi.notification_service.domain.notification.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DateResponseDto {
	private String date;
	private String time;

	public DateResponseDto(LocalDateTime createdAt) {
		this.date = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.time = createdAt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
}
