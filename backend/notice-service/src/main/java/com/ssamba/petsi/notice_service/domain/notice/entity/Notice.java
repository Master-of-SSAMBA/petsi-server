package com.ssamba.petsi.notice_service.domain.notice.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notice")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id", nullable = false)
	private Long noticeId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String category;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private boolean isRead;

	@Column(nullable = false)
	private Long link;

	@Column(nullable = false)
	private LocalDate createdAt;

	@Column(nullable = false)
	private LocalDate readAt;

}
