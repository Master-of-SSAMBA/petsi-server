package com.ssamba.petsi.schedule_service.domain.schedule.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamba.petsi.schedule_service.domain.schedule.dto.response.GetScheduleCategoryResponseDto;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;
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
			return scheduleCategoryRespository.findAllByUserId(userId).stream()
				.map(GetScheduleCategoryResponseDto::fromEntity)
				.collect(Collectors.toList());
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public void addScheduleCategory(Long userId, String title) {

		if (scheduleCategoryRespository.existsByUserIdAndTitle(userId, title)) {
			throw new BusinessLogicException(ExceptionCode.DUPLICATED_SCHEDULE_CATEGORY);
		}
		ScheduleCategory scheduleCategory = new ScheduleCategory(userId, title);
		scheduleCategoryRespository.save(scheduleCategory);

	}
}
