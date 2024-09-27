package com.ssamba.petsi.schedule_service.domain.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Pet_To_Ended_Schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetToEndedSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pet_to_ended_schedule_id", nullable = false)
	private Long petToEndedScheduleId;

	@Column(nullable = false)
	private Long petId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ended_schedule_id", nullable = false)
	private EndedSchedule endedSchedule;

	public PetToEndedSchedule(Long petId, EndedSchedule endedSchedule) {
		this.petId = petId;
		this.endedSchedule = endedSchedule;
	}

}
