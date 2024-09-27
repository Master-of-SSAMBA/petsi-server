package com.ssamba.petsi.schedule_service.domain.schedule.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.UpdateScheduleCategoryRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetScheduleCategoryResponseDto;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.ScheduleCategoryRepository;
import com.ssamba.petsi.schedule_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.schedule_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleCategoryService {

	private final ScheduleCategoryRepository scheduleCategoryRepository;

	@Transactional(readOnly = true)
	public ScheduleCategory findById(Long id) {
		return scheduleCategoryRepository.findById(id).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.SCHEDULE_CATEGORY_NOT_FOUND)
		);
	}

	@Transactional(readOnly = true)
	public List<GetScheduleCategoryResponseDto> getScheduleCategory(Long userId) {

		return scheduleCategoryRepository.findAllByUserId(userId).stream()
			.map(GetScheduleCategoryResponseDto::fromEntity)
			.collect(Collectors.toList());
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

}
