package com.ssamba.petsi.notification_service.domain.notification.service;

import com.ssamba.petsi.notification_service.domain.notification.dto.request.TokenRequestDto;
import com.ssamba.petsi.notification_service.domain.notification.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {
    private final UserTokenRepository userTokenRepository;

    public void saveToken(Long userId, TokenRequestDto dto) {
        userTokenRepository.save(dto.toEntity(userId, dto));
    }

    public void deleteAllTokens(Long userId) {
        userTokenRepository.deleteAllByUserId(userId);
    }

    public void deleteToken(Long userId, TokenRequestDto dto) {
        userTokenRepository.deleteByUserIdAndToken(userId, dto.getToken());
    }
}
