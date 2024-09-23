package com.ssamba.petsi.schedule_service.domain.schedule.entity;

import java.util.List;

import com.ssamba.petsi.schedule_service.domain.schedule.enums.ScheduleStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Schedule_Category")
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

	@Column(nullable = false)
	private String status;

	@OneToMany(mappedBy = "scheduleCategory", cascade = CascadeType.ALL)
	private List<Schedule> schedules;

	@PrePersist
	protected void onCreate() {
		this.status = ScheduleStatus.ACTIVATED.getValue();
	}
	public ScheduleCategory(Long userId, String title) {
		this.userId = userId;
		this.title = title;
	}

}
