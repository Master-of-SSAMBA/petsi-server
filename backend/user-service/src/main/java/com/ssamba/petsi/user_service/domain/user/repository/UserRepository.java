package com.ssamba.petsi.user_service.domain.user.repository;

import com.ssamba.petsi.user_service.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    void deleteByEmail(String email);

    Optional<User> findByUserId(Long userId);
}
