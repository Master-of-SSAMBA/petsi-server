package com.ssamba.petsi.notice_service.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssamba.petsi.notice_service.domain.notice.entity.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
	void deleteAllByUserId(Long userId);

	void deleteByUserIdAndToken(Long userId, String token);
}
