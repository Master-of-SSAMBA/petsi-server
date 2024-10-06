package com.ssamba.petsi.account_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PictureMonthlyRequestDto {
	private Long userId;
	private int year;
	private int month;

}
