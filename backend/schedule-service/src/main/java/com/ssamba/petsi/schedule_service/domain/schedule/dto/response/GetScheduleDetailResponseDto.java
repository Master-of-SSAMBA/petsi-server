package com.ssamba.petsi.schedule_service.domain.schedule.dto.response;

import java.util.List;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;

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
	private String interval;

	public GetScheduleDetailResponseDto(Schedule schedule) {
		//todo : petList 갖고와서 변환
		this.title = schedule.getScheduleCategory().getTitle();
		this.dueDate = new DateResponseDto(schedule.getNextScheduleDate());
		this.interval = schedule.getIntervalDays();
	}
}
