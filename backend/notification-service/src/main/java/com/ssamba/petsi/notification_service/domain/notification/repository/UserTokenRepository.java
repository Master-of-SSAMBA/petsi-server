package com.ssamba.petsi.notification_service.domain.notification.repository;

import com.ssamba.petsi.notification_service.domain.notification.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
	void deleteAllByUserId(Long userId);

	void deleteByUserIdAndToken(Long userId, String token);

	List<UserToken> findUserTokensByUserId(Long userId);

	boolean existsByUserIdAndToken(Long userId, String token);
}
