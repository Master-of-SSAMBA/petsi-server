package com.ssamba.petsi.schedule_service.domain.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;

@Repository
public interface ScheduleCategoryRespository extends JpaRepository<ScheduleCategory, Long> {
	List<ScheduleCategory> findAllByUserId(Long userId);

	boolean existsByUserIdAndTitle(Long userId, String title);
}
