package com.ssamba.petsi.user_service.domain.user.dto.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RegisterKeycloakUserRequestDto {
    private String email;
    private String password;
    private Long userId;
    private String userKey;

    public static RegisterKeycloakUserRequestDto create(SignupRequestDto signupRequestDto, Long userId, String userKey) {
     return RegisterKeycloakUserRequestDto.builder()
             .email(signupRequestDto.getEmail())
             .password(signupRequestDto.getPassword())
             .userId(userId)
             .userKey(userKey)
             .build();
    }
}
