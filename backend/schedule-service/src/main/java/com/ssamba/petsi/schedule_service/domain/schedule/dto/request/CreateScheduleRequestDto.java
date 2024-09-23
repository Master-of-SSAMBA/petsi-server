package com.ssamba.petsi.schedule_service.domain.schedule.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateScheduleRequestDto {
	private List<Long> petId;
	@Length(max=255)
	private String description;
	private LocalDateTime startDate;
	private int intervalDays;

	public Schedule toSchedule() {
		Schedule schedule = new Schedule();
	}
}
