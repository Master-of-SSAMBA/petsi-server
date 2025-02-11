package com.ssamba.petsi.schedule_service.domain.schedule.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ssamba.petsi.schedule_service.domain.schedule.enums.ScheduleStatus;

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
import lombok.Setter;

@Entity
@Table(name = "Schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id", nullable = false)
	private Long scheduleId;

	@Setter
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "schedule_category_id", nullable = false)
	private ScheduleCategory scheduleCategory;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private String intervalType;

	@Column(nullable = false)
	private int intervalDay;

	@Setter
	@Column(nullable = false)
	private LocalDate nextScheduleDate;

	@Setter
	@Column(nullable = false)
	private String status;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<PetToSchedule> petToSchedule;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.status = ScheduleStatus.ACTIVATED.getValue();

	}

	public Schedule(String description, LocalDate startDate, String intervalType, int intervalDay) {
		this.description = description;
		this.startDate = startDate;
		this.nextScheduleDate = startDate;
		this.intervalType = intervalType;
		this.intervalDay = intervalDay;
	}
}
