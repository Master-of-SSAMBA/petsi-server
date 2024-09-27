package com.ssamba.petsi.schedule_service.domain.schedule.dto.response;

import java.util.List;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.IntervalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetScheduleDetailResponseDto {

	@Getter
	@AllArgsConstructor
	public static class Pet {
		String name;
		boolean isAssigned;
	}

	@Setter
	private List<Pet> pet;
	private String title;
	private DateResponseDto dueDate;
	private String intervalType;
	private int intervalDay;

	public GetScheduleDetailResponseDto(Schedule schedule) {
		//todo : petList 갖고와서 변환
		this.title = schedule.getDescription();
		this.dueDate = new DateResponseDto(schedule.getNextScheduleDate());
		this.intervalType = schedule.getIntervalType();
		this.intervalDay = schedule.getIntervalDay();
	}
}
