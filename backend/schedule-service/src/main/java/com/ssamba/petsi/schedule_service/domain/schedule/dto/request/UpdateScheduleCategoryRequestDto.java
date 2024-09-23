package com.ssamba.petsi.schedule_service.domain.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateScheduleCategoryRequestDto {
	private Long id;
	private String title;
}
