package com.ssamba.petsi.schedule_service.domain.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.EndedSchedule;

public interface EndedScheduleRepository extends JpaRepository<EndedSchedule, Long> {

	@Query("SELECT es FROM EndedSchedule es " +
		"JOIN es.schedule s " +
		"WHERE s.scheduleCategory.userId = :userId " +
		"AND FUNCTION('MONTH', es.createdAt) = :month ")
	List<EndedSchedule> getAllEndedScheduledList(Long userId, int month);

	@Query("SELECT es FROM EndedSchedule es " +
		"JOIN es.schedule s " +
		"JOIN PetToSchedule pts ON pts.schedule = s " +
		"WHERE s.scheduleCategory.userId = :userId " +
		"AND pts.petId = :petId " +
		"AND FUNCTION('MONTH', es.createdAt) = :month ")
	List<EndedSchedule> getAllEndedScheduledListWithPetId(Long userId, int month, Long petId);
}
