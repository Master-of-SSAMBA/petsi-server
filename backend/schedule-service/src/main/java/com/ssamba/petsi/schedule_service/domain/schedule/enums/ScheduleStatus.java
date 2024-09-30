package com.ssamba.petsi.schedule_service.domain.schedule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScheduleStatus {
	ACTIVATED("활성"),
	INACTIVATED("비활성"),
	ENDED("완료");

	private final String value;

}

