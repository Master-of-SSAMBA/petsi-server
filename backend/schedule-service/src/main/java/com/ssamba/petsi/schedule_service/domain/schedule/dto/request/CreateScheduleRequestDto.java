package com.ssamba.petsi.schedule_service.domain.schedule.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.PetToSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.IntervalType;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CreateScheduleRequestDto {
	private Long scheduleCategoryId;
	private List<Long> petId;
	@Setter
	@Length(max=255)
	private String description;
	private LocalDate startDate;
	private String intervalType;
	private int intervalDay;

	public Schedule toSchedule() {
		return new Schedule(
			this.description,
			this.startDate,
			this.intervalType,
			this.intervalDay
		);
	}

}
