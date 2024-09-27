package com.ssamba.petsi.schedule_service.domain.schedule.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateScheduleRequestDto {
	private Long scheduleId;
	private Long scheduleCategoryId;
	private String description;
	private String intervalType;
	private Integer intervalDay;
	private LocalDate nextScheduleDate;
	private List<Long> pets;

}

