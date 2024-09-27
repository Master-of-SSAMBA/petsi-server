package com.ssamba.petsi.schedule_service.domain.schedule.enums;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntervalType {
	PER_YEAR("년", 0) {
		@Override
		public LocalDate adjustDate(LocalDate date, int interval) {
			return date.plusYears(interval);
		}
	},
	PER_MONTH("월", 1) {
		@Override
		public LocalDate adjustDate(LocalDate date, int interval) {
			return date.plusMonths(interval);
		}
	},
	PER_WEEK("주", 2) {
		@Override
		public LocalDate adjustDate(LocalDate date, int interval) {
			return date.plusWeeks(interval);
		}
	},
	PER_DAY("일", 3) {
		@Override
		public LocalDate adjustDate(LocalDate date, int interval) {
			return date.plusDays(interval);
		}
	},
	SPEC_DAY("매달", 4) {
		@Override
		public LocalDate adjustDate(LocalDate date, int interval) {
			LocalDate nextMonthDate = date.plusMonths(1);
			int lastDayOfMonth = nextMonthDate.lengthOfMonth();
			int targetDay = Math.min(interval, lastDayOfMonth);
			return nextMonthDate.withDayOfMonth(targetDay);
		}
	};


	public final String value;
	public final int index;

	public abstract LocalDate adjustDate(LocalDate date, int interval);
}
