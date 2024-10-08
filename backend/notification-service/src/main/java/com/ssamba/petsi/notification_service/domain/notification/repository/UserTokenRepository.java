package com.ssamba.petsi.notification_service.domain.notification.repository;

import com.ssamba.petsi.notification_service.domain.notification.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
	void deleteAllByUserId(Long userId);

	void deleteByUserIdAndToken(Long userId, String token);
}
