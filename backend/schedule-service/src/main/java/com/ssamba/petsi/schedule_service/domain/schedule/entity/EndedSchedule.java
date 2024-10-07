package com.ssamba.petsi.schedule_service.domain.schedule.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Ended_Schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EndedSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ended_schedule_id", nullable = false)
	private Long endedScheduleId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String schedule_category_title;

	@Column(nullable = false)
	private String schedule_category_icon;

	@Column(nullable = false)
	private String schedule_description;

	@Column(nullable = false)
	private LocalDate createdAt;

	@OneToMany(mappedBy = "endedSchedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<PetToEndedSchedule> petToEndedSchedule;

	public EndedSchedule(Schedule schedule) {
		this.userId = schedule.getScheduleCategory().getUserId();
		this.schedule_category_title = schedule.getScheduleCategory().getTitle();
		this.schedule_category_icon = schedule.getScheduleCategory().getIcon();
		this.schedule_description = schedule.getDescription();
		this.createdAt = schedule.getNextScheduleDate();
	}
}
