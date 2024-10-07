package com.ssamba.petsi.notice_service.domain.notice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssamba.petsi.notice_service.domain.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

	@Query("SELECT n FROM Notice n WHERE n.userId = :userId ORDER BY n.isRead ASC, " +
		"CASE WHEN n.isRead = false THEN n.createdAt END DESC, " +
		"CASE WHEN n.isRead = true THEN n.readAt END DESC")
	List<Notice> findAllByUserIdWithCustomOrder(@Param("userId") Long userId);

	void deleteAllByUserIdAndIsRead(Long userId, boolean isRead);

	void deleteAllByUserId(Long userId);

	int countByUserIdAndIsRead(Long userId, boolean isRead);

	Optional<Notice> findByUserIdAndNoticeId(Long userId, Long noticeId);
}
