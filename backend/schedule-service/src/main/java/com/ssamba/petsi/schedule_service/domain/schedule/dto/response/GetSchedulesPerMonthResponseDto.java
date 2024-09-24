package com.ssamba.petsi.schedule_service.domain.schedule.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.EndedSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.PetToSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.ScheduleStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetSchedulesPerMonthResponseDto {
	private Long id;
	private String status;
	private DateResponseDto date;
	private List<Pet> pets;
	private String title;

	@AllArgsConstructor
	@Getter
	private static class Pet {
		Long petId;
		String petName;

		static Pet toPet(PetToSchedule petToSchedule) {
			return new Pet(petToSchedule.getPetId(), null);
		}
	}

	static public GetSchedulesPerMonthResponseDto fromScheduleEntity(Schedule schedule) {
		Long id = schedule.getScheduleId();
		String status = ScheduleStatus.UPCOMING.getValue();
		DateResponseDto date = new DateResponseDto(schedule.getNextScheduleDate());
		List<Pet> pets = schedule.getPetToSchedule().stream()
			.map(Pet::toPet)
			.collect(Collectors.toList());
		String title = schedule.getDescription();

		return new GetSchedulesPerMonthResponseDto(id, status, date, pets, title);
	}

	static public GetSchedulesPerMonthResponseDto fromEndedScheduleEntity(EndedSchedule endedSchedule) {
		Long id = endedSchedule.getEndedScheduleId();
		String status = ScheduleStatus.ENDED.getValue();
		DateResponseDto date = new DateResponseDto(endedSchedule.getCreatedAt());
		List<Pet> pets = endedSchedule.getSchedule().getPetToSchedule().stream()
			.map(Pet::toPet)
			.toList();
		String title = endedSchedule.getSchedule().getDescription();

		return new GetSchedulesPerMonthResponseDto(id, status, date, pets, title);
	}
}
