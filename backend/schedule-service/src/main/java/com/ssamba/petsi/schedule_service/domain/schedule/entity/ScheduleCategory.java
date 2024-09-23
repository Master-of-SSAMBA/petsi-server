package com.ssamba.petsi.schedule_service.domain.schedule.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ScheduleCategory")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_category_id", nullable = false)
	private Long scheduleCategoryId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String title;

	@OneToMany(mappedBy = "scheduleCategory", cascade = CascadeType.ALL)
	private List<Schedule> schedules;

}
