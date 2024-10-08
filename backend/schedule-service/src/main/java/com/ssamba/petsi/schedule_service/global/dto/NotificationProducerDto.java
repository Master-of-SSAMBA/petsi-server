package com.ssamba.petsi.schedule_service.global.dto;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationProducerDto {
	private Long userId;
	private String category;
	private String content;
	private Long id;

	public static NotificationProducerDto toNoticeProducerDto(Schedule schedule) {
		return new NotificationProducerDto(
			schedule.getScheduleCategory().getUserId(),
			"plan",
			"내일 " + schedule.getScheduleCategory().getTitle() + "-" + schedule.getDescription() + " 일정이 예정되어 있어요",
			schedule.getScheduleId());
	}
}
