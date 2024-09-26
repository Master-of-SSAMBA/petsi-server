package com.ssamba.petsi.schedule_service.domain.schedule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntervalType {
	PER_YEAR("년", 0),
	PER_MONTH("월", 1),
	PER_WEEK("주", 2),
	PER_DAY("일", 3),
	SPEC_DAY("매달 ", 4);

	private final String value;
	private final int index;

}
