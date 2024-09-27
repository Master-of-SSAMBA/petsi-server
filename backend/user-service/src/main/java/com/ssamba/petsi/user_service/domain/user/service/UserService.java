package com.ssamba.petsi.user_service.domain.user.service;

import com.ssamba.petsi.user_service.domain.user.dto.request.CreateKeycloakUserRequestDto;
import com.ssamba.petsi.user_service.domain.user.dto.request.SignupRequestDto;
import com.ssamba.petsi.user_service.domain.user.entity.User;
import com.ssamba.petsi.user_service.domain.user.repository.UserRepository;
import com.ssamba.petsi.user_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.user_service.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final FinApiService finApiService;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        isValidSignup(signupRequestDto);
        String userKey = finApiService.addMember(signupRequestDto.getEmail());
        User newUser = SignupRequestDto.toEntity(signupRequestDto, userKey);
        userRepository.save(newUser);

        String keycloakUserId = keycloakService.createUserInKeycloak(
                CreateKeycloakUserRequestDto.create(signupRequestDto, newUser.getUserId(), userKey));
        if (keycloakUserId == null) {
            userRepository.delete(newUser);
            throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void isValidSignup(SignupRequestDto signupRequestDto) {
        isValidEmail(signupRequestDto.getEmail());
        isValidNickname(signupRequestDto.getNickname());
    }

    private void isValidEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATED_EMAIL);
        }
    }

    private void isValidNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATED_NICKNAME);
        }
    }
}
