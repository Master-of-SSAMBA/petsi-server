package com.ssamba.petsi.schedule_service.domain.schedule.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.AddScheduleCategoryRequestDto;
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
	public void addScheduleCategory(Long userId, AddScheduleCategoryRequestDto dto) {
		ScheduleCategory category = scheduleCategoryRepository.findByUserIdAndTitle(userId, dto.getTitle());
		if (category != null) {
			throw new BusinessLogicException(ExceptionCode.DUPLICATED_SCHEDULE_CATEGORY);
		}
		scheduleCategoryRepository.save(new ScheduleCategory(userId, dto));
	}

	@Transactional
	public void deleteScheduleCategory(Long userId, Long id) {
		ScheduleCategory scheduleCategory = scheduleCategoryRepository.findByUserIdAndScheduleCategoryId(
			userId, id).orElseThrow(()
			-> new BusinessLogicException(ExceptionCode.SCHEDULE_CATEGORY_NOT_FOUND));

		scheduleCategoryRepository.delete(scheduleCategory);
	}

}
