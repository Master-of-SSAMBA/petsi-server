package com.ssamba.petsi.picture_service.domain.picture.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PictureMonthlyRequestDto {
	private Long userId;
	private int year;
	private int month;

}