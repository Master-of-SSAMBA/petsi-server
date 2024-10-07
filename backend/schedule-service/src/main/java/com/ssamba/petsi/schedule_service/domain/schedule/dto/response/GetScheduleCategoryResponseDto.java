package com.ssamba.petsi.schedule_service.domain.schedule.dto.response;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetScheduleCategoryResponseDto {
	private Long id;
	private String title;
	private String icon;

	public static GetScheduleCategoryResponseDto fromEntity(ScheduleCategory scheduleCategory) {
		return new GetScheduleCategoryResponseDto(
			scheduleCategory.getScheduleCategoryId(),
			scheduleCategory.getTitle(),
			scheduleCategory.getIcon()
		);
	}
}
