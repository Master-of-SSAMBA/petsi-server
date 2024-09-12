package com.ssamba.petsi.account_service.domain.account.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DateResponseDto {
	private String date;
	private String time;

	DateResponseDto(LocalDateTime dateTime) {

	}

}
