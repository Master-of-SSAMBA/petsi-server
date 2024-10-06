package com.ssamba.petsi.notice_service.domain.notice.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DateResponseDto {
	private String date;
	private String time;

	public DateResponseDto(LocalDate date) {
		this.date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public DateResponseDto(LocalDateTime createdAt) {
		this.date = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
