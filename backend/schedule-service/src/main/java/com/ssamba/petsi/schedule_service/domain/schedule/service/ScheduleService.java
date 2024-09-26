package com.ssamba.petsi.schedule_service.domain.schedule.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.CreateScheduleRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.UpdateScheduleCategoryRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.UpdateScheduleRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetScheduleCategoryResponseDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetScheduleDetailResponseDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetSchedulesDetailPerMonthResponseDto;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.EndedSchedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.IntervalType;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.ScheduleStatus;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.EndedScheduleRepository;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.ScheduleCategoryRepository;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.ScheduleRepository;
import com.ssamba.petsi.schedule_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.schedule_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

	private final ScheduleCategoryRepository scheduleCategoryRepository;
	private final ScheduleRepository scheduleRepository;
	private final EndedScheduleRepository endedScheduleRepository;

	@Transactional(readOnly = true)
	public List<GetScheduleCategoryResponseDto> getScheduleCategory(Long userId) {
		try {
			return scheduleCategoryRepository.findAllByUserId(userId).stream()
				.map(GetScheduleCategoryResponseDto::fromEntity)
				.collect(Collectors.toList());
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public void addScheduleCategory(Long userId, String title) {
		ScheduleCategory category = scheduleCategoryRepository.findByUserIdAndTitle(userId, title);
		if (category != null) {
			throw new BusinessLogicException(ExceptionCode.DUPLICATED_SCHEDULE_CATEGORY);
		}
		ScheduleCategory scheduleCategory = new ScheduleCategory(userId, title);
		scheduleCategoryRepository.save(scheduleCategory);
	}

	@Transactional
	public void deleteScheduleCategory(Long userId, Long id) {
		ScheduleCategory scheduleCategory = scheduleCategoryRepository.findByUserIdAndScheduleCategoryId(
			userId, id).orElseThrow(()
			-> new BusinessLogicException(ExceptionCode.SCHEDULE_CATEGORY_NOT_FOUND));

		scheduleCategoryRepository.delete(scheduleCategory);
	}

	@Transactional
	@Deprecated
	public void updateScheduleCategory(Long userId, UpdateScheduleCategoryRequestDto requestDto) {
		ScheduleCategory scheduleCategory = scheduleCategoryRepository.findByUserIdAndScheduleCategoryId(
			userId, requestDto.getId()).orElseThrow(
				() -> new BusinessLogicException(ExceptionCode.SCHEDULE_CATEGORY_NOT_FOUND));

		if (scheduleCategoryRepository.existsByUserIdAndTitle(userId, requestDto.getTitle())) {
			throw new BusinessLogicException(ExceptionCode.DUPLICATED_SCHEDULE_CATEGORY);
		}

		scheduleCategory.setTitle(requestDto.getTitle());

	}

	@Transactional(readOnly = true)
	public List<GetSchedulesDetailPerMonthResponseDto> getSchedulesPerMonth(Long userId, int month, Long petId) {
		List<Schedule> scheduledList = petId == null ? scheduleRepository.getAllScheduledList(userId, month)
			: scheduleRepository.getAllScheduledListWithPetId(userId, month, petId);

		List<EndedSchedule> endedScheduleList = petId == null ?
			endedScheduleRepository.getAllEndedScheduledList(userId, month)
			: endedScheduleRepository.getAllEndedScheduledListWithPetId(userId, month, petId);

		List<GetSchedulesDetailPerMonthResponseDto> mapScheduleList = scheduledList.stream()
			.map(GetSchedulesDetailPerMonthResponseDto::fromScheduleEntity)
			.toList();

		List<GetSchedulesDetailPerMonthResponseDto> mapEndedScheduleList = endedScheduleList.stream()
			.map(GetSchedulesDetailPerMonthResponseDto::fromEndedScheduleEntity)
			.toList();

		List<GetSchedulesDetailPerMonthResponseDto> returnList = new ArrayList<>(mapScheduleList);
		returnList.addAll(mapEndedScheduleList);

		return returnList;
	}

	@Transactional(readOnly = true)
	public GetScheduleDetailResponseDto getScheduleDetail(Long userId, Long id, String status) {
		Schedule schedule = scheduleRepository.findByScheduleIdAndScheduleCategoryUserId(id, userId).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND));

		//todo: 쿼리문 상에서 에러 던지는 걸로 수정
		if(schedule.getStatus().equals(ScheduleStatus.INACTIVATED.getValue())) {
			throw new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND);
		}

		GetScheduleDetailResponseDto dto = new GetScheduleDetailResponseDto(schedule);
		//todo : petList 갖고와서 dto에 set

		return dto;
	}

	@Transactional
	public void deleteSchedule(Long userId, Long id) {
		Schedule schedule = scheduleRepository.findByScheduleIdAndScheduleCategoryUserId(id, userId).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND)
		);

		schedule.setStatus(ScheduleStatus.INACTIVATED.getValue());
	}

	@Transactional
	public void createSchedule(Long userId, CreateScheduleRequestDto createScheduleRequestDto) {

		ScheduleCategory scheduleCategory = scheduleCategoryRepository.findByUserIdAndScheduleCategoryId(
			userId, createScheduleRequestDto.getScheduleCategoryId()).orElseThrow(()
			-> new BusinessLogicException(ExceptionCode.SCHEDULE_CATEGORY_NOT_FOUND)
		);

		//같은 일정이 존재한다면, 중복 오류
		if(scheduleRepository.existsByDescriptionAndScheduleCategory(createScheduleRequestDto.getDescription(), scheduleCategory)) {
			throw new BusinessLogicException(ExceptionCode.DUPLICATED_SCHEDULE);
		}

		Schedule schedule = createScheduleRequestDto.toSchedule();
		schedule.setScheduleCategory(scheduleCategory);
		scheduleRepository.save(schedule);

	}

	@Transactional
	public void updateSchedule(UpdateScheduleRequestDto updateScheduleRequestDto) throws IllegalAccessException {
		//todo: 일정 수정 코드 작성

		Schedule existingSchedule = scheduleRepository.findById(updateScheduleRequestDto.getScheduleId())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND));

		for (Field dtoField : UpdateScheduleRequestDto.class.getDeclaredFields()) {
			dtoField.setAccessible(true);
			Object newValue = dtoField.get(updateScheduleRequestDto);

			if (newValue != null) {
				try {
					if (dtoField.getName().equals("scheduleCategoryId")) {
						Long categoryId = (Long) newValue;
						ScheduleCategory newCategory = scheduleCategoryRepository.findById(categoryId)
							.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SCHEDULE_CATEGORY_NOT_FOUND));
						existingSchedule.setScheduleCategory(newCategory);
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
		Schedule schedule = scheduleRepository.findByScheduleIdAndScheduleCategoryUserId(scheduleId, userId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SCHEDULE_NOT_FOUND));

		EndedSchedule endedSchedule = new EndedSchedule(schedule);
		endedScheduleRepository.save(endedSchedule);

		if(schedule.getIntervalType().equals(IntervalType.PER_YEAR.name())) {
			//년도 변경
			schedule.setNextScheduleDate(schedule.getNextScheduleDate().plusYears(schedule.getIntervalDay()));
		} else if(schedule.getIntervalType().equals(IntervalType.PER_WEEK.name())) {
			//주 변경
			schedule.setNextScheduleDate(schedule.getNextScheduleDate().plusWeeks(schedule.getIntervalDay()));
		} else if(schedule.getIntervalType().equals(IntervalType.PER_MONTH.name())) {
			//달 변경
			schedule.setNextScheduleDate(schedule.getNextScheduleDate().plusMonths(schedule.getIntervalDay()));
		} else if(schedule.getIntervalType().equals(IntervalType.PER_DAY.name())) {
			//일 변경
			schedule.setNextScheduleDate(schedule.getNextScheduleDate().plusDays(schedule.getIntervalDay()));
		} else if(schedule.getIntervalType().equals(IntervalType.SPEC_DAY.name())) {
			//날짜 변경
		}


	}

}
