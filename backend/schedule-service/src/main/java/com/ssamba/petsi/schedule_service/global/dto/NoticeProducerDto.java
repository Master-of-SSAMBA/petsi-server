package com.ssamba.petsi.schedule_service.global.dto;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeProducerDto<T> {
	private Long userId;
	private String category;
	private T content;
	private Long id;

	public static NoticeProducerDto<String> toNoticeProducerDto(Schedule schedule) {
		return new NoticeProducerDto<String>(
			schedule.getScheduleCategory().getUserId(),
			"schedule",
			"",
			schedule.getScheduleId());
	}
}
