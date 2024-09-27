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
public class CreateKeycloakUserRequestDto {
    private String email;
    private String password;
    private Long userId;
    private String userKey;

    public static CreateKeycloakUserRequestDto create(SignupRequestDto signupRequestDto, Long userId, String userKey) {
     return CreateKeycloakUserRequestDto.builder()
             .email(signupRequestDto.getEmail())
             .password(signupRequestDto.getPassword())
             .userId(userId)
             .userKey(userKey)
             .build();
    }

    public static Map<String, Object> createKeycloakUser(CreateKeycloakUserRequestDto createKeycloakUserRequestDto) {
        Map<String, Object> keycloakUser = new HashMap<>();
        keycloakUser.put("username", createKeycloakUserRequestDto.getEmail());
        keycloakUser.put("email", createKeycloakUserRequestDto.getEmail());
        keycloakUser.put("enabled", false); // 이메일 인증 완료 전까지 비활성화
        keycloakUser.put("credentials", Arrays.asList(Map.of(
                "type", "password",
                "value", createKeycloakUserRequestDto.getPassword(),
                "temporary", false
        )));
        keycloakUser.put("attributes", Map.of(
                "user_id", String.valueOf(createKeycloakUserRequestDto.getUserId()),
                "user_key", createKeycloakUserRequestDto.getUserKey()
        ));
        return keycloakUser;
    }
}
