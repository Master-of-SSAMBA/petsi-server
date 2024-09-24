package com.ssamba.petsi.schedule_service.domain.schedule.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	Optional<Schedule> findByScheduleIdAndScheduleCategoryUserId(Long id, Long userId);

	@Query("SELECT s FROM Schedule s " +
		"JOIN s.scheduleCategory sc " +
		"JOIN s.petToSchedule ps " +
		"WHERE sc.userId = :userId " +
		"AND FUNCTION('MONTH', s.nextScheduleDate) = :month ")
	List<Schedule> getAllScheduledList(Long userId, int month);

	@Query("SELECT s FROM Schedule s " +
		"JOIN s.scheduleCategory sc " +
		"JOIN s.petToSchedule ps " +
		"WHERE sc.userId = :userId " +
		"AND FUNCTION('MONTH', s.nextScheduleDate) = :month " +
		"AND ps.petId = :petId")
	List<Schedule> getAllScheduledListWithPetId(@Param("userId") Long userId,
		@Param("month") int month,
		@Param("petId") Long petId);

}
