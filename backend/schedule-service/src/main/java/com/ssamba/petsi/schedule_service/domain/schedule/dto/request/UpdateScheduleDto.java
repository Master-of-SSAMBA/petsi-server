package com.ssamba.petsi.schedule_service.domain.schedule.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UpdateScheduleDto {
	private Long scheduleId;
	private ScheduleCategory scheduleCategory;
	private String description;
	private String intervalType;
	private Integer intervalDay;
	private LocalDate nextScheduleDate;
	@Setter
	private List<Long> pets;

	public UpdateScheduleDto(UpdateScheduleRequestDto updateScheduleRequestDto, ScheduleCategory category) {
		this.scheduleId = updateScheduleRequestDto.getScheduleId();
		this.scheduleCategory = category;
		this.description = updateScheduleRequestDto.getDescription();
		this.intervalType = updateScheduleRequestDto.getIntervalType();
		this.intervalDay = updateScheduleRequestDto.getIntervalDay();
		this.nextScheduleDate = updateScheduleRequestDto.getNextScheduleDate();
		this.pets = updateScheduleRequestDto.getPets();
	}
}
