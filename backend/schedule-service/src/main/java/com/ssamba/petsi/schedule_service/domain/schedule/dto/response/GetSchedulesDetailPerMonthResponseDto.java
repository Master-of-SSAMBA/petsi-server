package com.ssamba.petsi.schedule_service.domain.schedule.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.EndedSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.PetToEndedSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.PetToSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.ScheduleStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetSchedulesDetailPerMonthResponseDto {
	private Long scheduleId;
	private String status;
	private DateResponseDto date;
	private List<Pet> pets;
	private GetScheduleCategoryResponseDto scheduleCategory;
	private String description;

	@AllArgsConstructor
	@Getter
	private static class Pet {
		Long petId;
		String petName;

		static Pet toPet(PetToSchedule petToSchedule) {
			return new Pet(petToSchedule.getPetId(), null);
		}

		static Pet fromEndedScheduletoPet(PetToEndedSchedule petToEndedSchedule) {
			return new Pet(petToEndedSchedule.getPetId(), null);
		}
	}

	static public GetSchedulesDetailPerMonthResponseDto fromScheduleEntity(Schedule schedule) {
		Long id = schedule.getScheduleId();
		String status = ScheduleStatus.ACTIVATED.getValue();
		DateResponseDto date = new DateResponseDto(schedule.getNextScheduleDate());
		List<Pet> pets = schedule.getPetToSchedule().stream()
			.map(Pet::toPet)
			.collect(Collectors.toList());

		String title = schedule.getDescription();
		GetScheduleCategoryResponseDto scheduleCategory = GetScheduleCategoryResponseDto.fromEntity(schedule.getScheduleCategory());
		return new GetSchedulesDetailPerMonthResponseDto(id, status, date, pets, scheduleCategory, title);
	}

	static public GetSchedulesDetailPerMonthResponseDto fromEndedScheduleEntity(EndedSchedule endedSchedule) {
		Long id = endedSchedule.getEndedScheduleId();
		String status = ScheduleStatus.ENDED.getValue();
		DateResponseDto date = new DateResponseDto(endedSchedule.getCreatedAt());
		List<Pet> pets = endedSchedule.getPetToEndedSchedule().stream()
			.map(Pet::fromEndedScheduletoPet)
			.toList();
		String title = endedSchedule.getSchedule_description();
		GetScheduleCategoryResponseDto scheduleCategory = new GetScheduleCategoryResponseDto(null, endedSchedule.getSchedule_category_title());
		return new GetSchedulesDetailPerMonthResponseDto(id, status, date, pets, scheduleCategory, title);
	}

	public List<PetToSchedule> toPetToSchedule(List<Long> pets, Schedule schedule) {
		return pets.stream()
			.map(petId -> PetToSchedule.builder()
				.petId(petId)
				.schedule(schedule)
				.build())
			.collect(Collectors.toList());
	}

}
