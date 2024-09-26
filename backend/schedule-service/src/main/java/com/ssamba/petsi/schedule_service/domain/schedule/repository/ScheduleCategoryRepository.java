package com.ssamba.petsi.schedule_service.domain.schedule.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;

@Repository
public interface ScheduleCategoryRepository extends JpaRepository<ScheduleCategory, Long> {
	List<ScheduleCategory> findAllByUserId(Long userId);

	ScheduleCategory findByUserIdAndTitle(Long userId, String title);

	Optional<ScheduleCategory> findByUserIdAndScheduleCategoryId (
		Long userId, Long scheduleCategoryId);

	boolean existsByUserIdAndTitle(Long userId, String title);

}
