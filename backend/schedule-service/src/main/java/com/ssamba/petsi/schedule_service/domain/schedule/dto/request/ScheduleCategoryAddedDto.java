package com.ssamba.petsi.schedule_service.domain.schedule.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleCategoryAddedDto extends UpdateScheduleRequestDto{
	private Long scheduleId;
	private ScheduleCategory scheduleCategory;
	private String description;
	private String intervalType;
	private Integer intervalDay;
	private LocalDate nextScheduleDate;
	private List<Long> pets;

	public ScheduleCategoryAddedDto(UpdateScheduleRequestDto usr, ScheduleCategory scheduleCategory) {
		this.scheduleId = usr.getScheduleCategoryId();
		this.scheduleCategory = scheduleCategory;
		this.description = usr.getDescription();
		this.intervalType = usr.getIntervalType();
		this.intervalDay = usr.getIntervalDay();
		this.nextScheduleDate = usr.getNextScheduleDate();
		this.pets = usr.getPets();
	}
}
