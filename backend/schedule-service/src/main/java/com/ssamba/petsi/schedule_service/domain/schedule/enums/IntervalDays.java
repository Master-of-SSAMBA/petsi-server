package com.ssamba.petsi.schedule_service.domain.schedule.enums;

public enum IntervalDays {
	PER_MONTH("한 달"),
	PER_YEAR("일 년"),
	UPCOMING("예정"),
	ENDED("완료");

	private final String value;

	IntervalDays(String value) {
		this.value = value;
	}
}
