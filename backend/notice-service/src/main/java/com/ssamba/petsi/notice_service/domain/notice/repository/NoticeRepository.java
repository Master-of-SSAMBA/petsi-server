package com.ssamba.petsi.notice_service.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssamba.petsi.notice_service.domain.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
