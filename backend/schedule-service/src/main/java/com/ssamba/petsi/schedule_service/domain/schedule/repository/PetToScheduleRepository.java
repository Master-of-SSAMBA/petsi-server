package com.ssamba.petsi.schedule_service.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.PetToSchedule;

public interface PetToScheduleRepository extends JpaRepository<PetToSchedule, Long> {
	void deleteByScheduleScheduleId(Long scheduleId);
}
