package com.ssamba.petsi.schedule_service.domain.schedule.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.UpdateScheduleCategoryRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetScheduleCategoryResponseDto;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.ScheduleStatus;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.ScheduleCategoryRespository;
import com.ssamba.petsi.schedule_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.schedule_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

	private final ScheduleCategoryRespository scheduleCategoryRespository;

	@Transactional(readOnly = true)
	public List<GetScheduleCategoryResponseDto> getScheduleCategory(Long userId) {
		try {
			return scheduleCategoryRespository.findAllByUserIdAndStatus(userId, ScheduleStatus.ACTIVATED.getValue()).stream()
				.map(GetScheduleCategoryResponseDto::fromEntity)
				.collect(Collectors.toList());
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public void addScheduleCategory(Long userId, String title) {

		ScheduleCategory category = scheduleCategoryRespository.findByUserIdAndTitle(userId, title);
		if (category != null) {
			if(category.getStatus().equals(ScheduleStatus.ACTIVATED.getValue())) {
				throw new BusinessLogicException(ExceptionCode.DUPLICATED_SCHEDULE_CATEGORY);
			}
			category.setStatus(ScheduleStatus.ACTIVATED.getValue()); return;
		}
		ScheduleCategory scheduleCategory = new ScheduleCategory(userId, title);
		scheduleCategoryRespository.save(scheduleCategory);

	}

	@Transactional
	public void deleteScheduleCategory(Long userId, Long id) {
		ScheduleCategory scheduleCategory = scheduleCategoryRespository.findByUserIdAndScheduleCategoryIdAndStatus(
			userId, id, ScheduleStatus.ACTIVATED.getValue()).orElseThrow(()
			-> new BusinessLogicException(ExceptionCode.SCHEDULE_CATEGORY_NOT_FOUND));

		scheduleCategory.setStatus(ScheduleStatus.INACTIVATED.getValue());

	}

	@Transactional
	public void updateScheduleCategory(Long userId, UpdateScheduleCategoryRequestDto requestDto) {
		ScheduleCategory scheduleCategory = scheduleCategoryRespository.findByUserIdAndScheduleCategoryIdAndStatus(
			userId, requestDto.getId(), ScheduleStatus.ACTIVATED.getValue()).orElseThrow(
				() -> new BusinessLogicException(ExceptionCode.SCHEDULE_CATEGORY_NOT_FOUND));

		scheduleCategory.setTitle(requestDto.getTitle());

	}
}
