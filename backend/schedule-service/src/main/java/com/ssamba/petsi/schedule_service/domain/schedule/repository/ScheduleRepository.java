package com.ssamba.petsi.schedule_service.domain.schedule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	Optional<Schedule> findByScheduleIdAndScheduleCategoryUserId(Long id, Long userId);
}
