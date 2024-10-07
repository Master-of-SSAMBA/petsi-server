package com.ssamba.petsi.schedule_service.domain.schedule.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.EndedSchedule;

public interface EndedScheduleRepository extends JpaRepository<EndedSchedule, Long> {

	@Query("SELECT es FROM EndedSchedule es " +
		"WHERE es.userId = :userId " +
		"AND es.createdAt between :startDate and :endDate " +
		"ORDER BY es.createdAt DESC")
	List<EndedSchedule> getAllEndedScheduledList(Long userId, LocalDate startDate, LocalDate endDate);

	@Query("SELECT es FROM EndedSchedule es " +
		"JOIN PetToEndedSchedule ptes " +
		"ON ptes.endedSchedule = es " +
		"WHERE es.userId = :userId " +
		"AND ptes.petId = :petId " +
		"AND es.createdAt between :startDate and :endDate " +
		"ORDER BY es.createdAt DESC")
	List<EndedSchedule> getAllEndedScheduledListWithPetId(Long userId, LocalDate startDate, LocalDate endDate, Long petId);

	Optional<EndedSchedule> findByEndedScheduleIdAndUserId(Long endedScheduleId, Long userId);
}
