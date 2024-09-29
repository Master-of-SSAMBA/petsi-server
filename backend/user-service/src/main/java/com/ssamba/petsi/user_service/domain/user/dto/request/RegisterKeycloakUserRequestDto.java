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

    public static Map<String, Object> createKeycloakUser(RegisterKeycloakUserRequestDto registerKeycloakUserRequestDto) {
        Map<String, Object> keycloakUser = new HashMap<>();
        keycloakUser.put("username", registerKeycloakUserRequestDto.getEmail());
        keycloakUser.put("email", registerKeycloakUserRequestDto.getEmail());
        keycloakUser.put("enabled", false); // 이메일 인증 완료 전까지 비활성화
        keycloakUser.put("credentials", Arrays.asList(Map.of(
                "type", "password",
                "value", registerKeycloakUserRequestDto.getPassword(),
                "temporary", false
        )));
        keycloakUser.put("attributes", Map.of(
                "user_id", String.valueOf(registerKeycloakUserRequestDto.getUserId()),
                "user_key", registerKeycloakUserRequestDto.getUserKey()
        ));
        return keycloakUser;
    }
}
