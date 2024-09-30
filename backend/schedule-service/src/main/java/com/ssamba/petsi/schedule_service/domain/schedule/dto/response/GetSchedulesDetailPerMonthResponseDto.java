package com.ssamba.petsi.schedule_service.domain.schedule.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.EndedSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.PetToEndedSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.PetToSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.ScheduleStatus;
import com.ssamba.petsi.schedule_service.global.dto.PetCustomDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class GetSchedulesDetailPerMonthResponseDto<T> {

	private Long scheduleId;
	private String status;
	private DateResponseDto date;
	@Setter
	private List<T> pets;
	private GetScheduleCategoryResponseDto scheduleCategory;
	private String description;

	public static GetSchedulesDetailPerMonthResponseDto<Long> fromScheduleEntity(Schedule schedule) {
		Long id = schedule.getScheduleId();
		String status = ScheduleStatus.ACTIVATED.getValue();
		DateResponseDto date = new DateResponseDto(schedule.getNextScheduleDate());
		String title = schedule.getDescription();
		List<Long> pets = schedule.getPetToSchedule().stream().map(PetToSchedule::getPetId).toList();
		GetScheduleCategoryResponseDto scheduleCategory = GetScheduleCategoryResponseDto.fromEntity(schedule.getScheduleCategory());
		return new GetSchedulesDetailPerMonthResponseDto(id, status, date, pets, scheduleCategory, title);
	}

	public static GetSchedulesDetailPerMonthResponseDto<Long> fromEndedScheduleEntity(EndedSchedule endedSchedule) {
		Long id = endedSchedule.getEndedScheduleId();
		String status = ScheduleStatus.ENDED.getValue();
		DateResponseDto date = new DateResponseDto(endedSchedule.getCreatedAt());
		String title = endedSchedule.getSchedule_description();
		List<Long> pets = endedSchedule.getPetToEndedSchedule().stream().map(PetToEndedSchedule::getPetId).toList();
		GetScheduleCategoryResponseDto scheduleCategory = new GetScheduleCategoryResponseDto(null, endedSchedule.getSchedule_category_title());
		return new GetSchedulesDetailPerMonthResponseDto(id, status, date, pets, scheduleCategory, title);
	}

	public static GetSchedulesDetailPerMonthResponseDto<PetCustomDto> toDto(
		GetSchedulesDetailPerMonthResponseDto<Long> dto, List<PetCustomDto> pcd) {
		return new GetSchedulesDetailPerMonthResponseDto<PetCustomDto>(
			dto.getScheduleId(),
			dto.getStatus(),
			dto.getDate(),
			pcd,
			dto.getScheduleCategory(),
			dto.getDescription()
		);
	}

}



