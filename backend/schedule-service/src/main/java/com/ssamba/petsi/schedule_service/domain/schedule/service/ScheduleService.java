package com.ssamba.petsi.schedule_service.domain.schedule.service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.CreateScheduleRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.UpdateScheduleCategoryRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.UpdateScheduleDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.UpdateScheduleRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetScheduleCategoryResponseDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetScheduleDetailResponseDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetSchedulesDetailPerMonthResponseDto;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.EndedSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.PetToEndedSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.PetToSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.IntervalType;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.ScheduleStatus;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.EndedScheduleRepository;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.PetToEndedScheduleRepository;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.PetToScheduleRepository;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.ScheduleCategoryRepository;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.ScheduleRepository;
import com.ssamba.petsi.schedule_service.global.client.PetClient;
import com.ssamba.petsi.schedule_service.global.dto.PetCustomDto;
import com.ssamba.petsi.schedule_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.schedule_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final EndedScheduleRepository endedScheduleRepository;
	private final PetToScheduleRepository petToScheduleRepository;
	private final PetToEndedScheduleRepository petToEndedScheduleRepository;
	private final PetClient petClient;

	@Transactional(readOnly = true)
	public List<GetSchedulesDetailPerMonthResponseDto> getUpcomingSchedulesPerMonth(Long userId, int month, Long petId) {
		List<Schedule> scheduledList = petId == null ? scheduleRepository.getAllScheduledList(userId, month,
			ScheduleStatus.ACTIVATED.getValue())
			: scheduleRepository.getAllScheduledListWithPetId(userId, month, petId,
			ScheduleStatus.ACTIVATED.getValue());

		return scheduledList.stream()
			.map(GetSchedulesDetailPerMonthResponseDto::fromScheduleEntity)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<GetSchedulesDetailPerMonthResponseDto> getEndedSchedulesPerMonth(Long userId, int month, Long petId) {
		List<EndedSchedule> endedScheduleList = petId == null ?
			endedScheduleRepository.getAllEndedScheduledList(userId, month)
			: endedScheduleRepository.getAllEndedScheduledListWithPetId(userId, month, petId);

		return endedScheduleList.stream()
			.map(GetSchedulesDetailPerMonthResponseDto::fromEndedScheduleEntity)
			.toList();
	}

	@Transactional(readOnly = true)
	public GetScheduleDetailResponseDto getScheduleDetail(Long userId, Long id) {
		Schedule schedule = scheduleRepository.findByScheduleIdAndScheduleCategoryUserIdAndStatus(id, userId,
			ScheduleStatus.ACTIVATED.getValue()).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND));

		GetScheduleDetailResponseDto dto = new GetScheduleDetailResponseDto(schedule);

		//todo : petList 갖고와서 dto에 set
		List<PetCustomDto> petList = petClient.findAll(userId);
		dto.setPet(GetScheduleDetailResponseDto.PetWithStatus
			.createPetWithStatusList(petList, schedule.getPetToSchedule().stream().map(PetToSchedule::getPetId).toList()));

		return dto;
	}

	@Transactional
	public void deleteSchedule(Long userId, Long id) {
		Schedule schedule = scheduleRepository.findByScheduleIdAndScheduleCategoryUserIdAndStatus(id, userId,
			ScheduleStatus.ACTIVATED.getValue()).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND)
		);

		schedule.setStatus(ScheduleStatus.INACTIVATED.getValue());
	}

	@Transactional
	public void createSchedule(Long userId, CreateScheduleRequestDto createScheduleRequestDto, ScheduleCategory category) {

		if(scheduleRepository.existsByDescriptionAndScheduleCategory(createScheduleRequestDto.getDescription(), category)) {
			throw new BusinessLogicException(ExceptionCode.DUPLICATED_SCHEDULE);
		}

		Schedule schedule = createScheduleRequestDto.toSchedule();
		schedule.setScheduleCategory(category);
		scheduleRepository.save(schedule);

		addPetToSchedule(createScheduleRequestDto.getPetId(), schedule);
	}

	@Transactional
	public void addPetToSchedule(List<Long> pets, Schedule schedule) {
		pets.stream()
			.map(id -> new PetToSchedule(id, schedule))
			.forEach(petToScheduleRepository::save);
	}

	@Transactional
	public void updateSchedule(UpdateScheduleDto updateScheduleDto) throws IllegalAccessException {

		Schedule existingSchedule = scheduleRepository.findById(updateScheduleDto.getScheduleId())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND));

		for (Field dtoField : UpdateScheduleDto.class.getDeclaredFields()) {
			dtoField.setAccessible(true);
			Object newValue = dtoField.get(updateScheduleDto);

			if (newValue != null) {
				try {
					//todo: petService에서 받아온 값으로 대체
					if (dtoField.getName().equals("pets")) {
						petToScheduleRepository.deleteByScheduleScheduleId(existingSchedule.getScheduleId());
						addPetToSchedule(updateScheduleDto.getPets(), existingSchedule);
						continue;
					}
					Field entityField = Schedule.class.getDeclaredField(dtoField.getName());
					entityField.setAccessible(true);
					entityField.set(existingSchedule, newValue);
				} catch (NoSuchFieldException e) {
					throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
				}
			}
		}

	}

	@Transactional
	public void finishSchedule(Long userId, Long scheduleId) {
		Schedule schedule = scheduleRepository.findByScheduleIdAndScheduleCategoryUserIdAndStatus(scheduleId, userId,
				ScheduleStatus.ACTIVATED.getValue())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND));

		if(schedule.getNextScheduleDate().isAfter(LocalDate.now())) {
			throw new BusinessLogicException(ExceptionCode.INVALID_DATE);
		}

		EndedSchedule endedSchedule = new EndedSchedule(schedule);
		endedScheduleRepository.save(endedSchedule);

		schedule.getPetToSchedule().stream()
			.map(id -> new PetToEndedSchedule(id.getPetId(), endedSchedule))
			.forEach(petToEndedScheduleRepository::save);

		schedule.setNextScheduleDate(
			IntervalType.valueOf(schedule.getIntervalType()).adjustDate(
				schedule.getNextScheduleDate(), schedule.getIntervalDay()));
	}

	@Transactional
	public void deleteFinishedSchedule(Long userId, Long endedScheduleId) {
		EndedSchedule endedSchedule = endedScheduleRepository.findByEndedScheduleIdAndUserId(
			endedScheduleId, userId).orElseThrow(()
			-> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND)
		);
		endedScheduleRepository.delete(endedSchedule);
	}
}
