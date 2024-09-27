package com.ssamba.petsi.schedule_service.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.PetToEndedSchedule;

public interface PetToEndedScheduleRepository extends JpaRepository<PetToEndedSchedule, Long> {
}
