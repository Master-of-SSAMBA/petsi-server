package com.ssamba.petsi.user_service.domain.user.dto.request;

import com.ssamba.petsi.user_service.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "이메일을 입력하세요!")
    // 이메일 형식 아이디@도메인이름.최상위도메인
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,50}$", message = "유효하지 않은 이메일입니다!")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=]).{8,20}$", message = "유효하지 않은 비밀번호입니다!")
    private String password;
    @NotBlank(message = "이름을 입력하세요!")
    @Size(min = 1, max = 5, message = "사용자 이름은 최소 1글자, 최대 5글자여야 합니다.")
    private String name;
    @NotBlank(message = "닉네임을 입력하세요!")
    @Size(min = 1, max = 10, message = "닉네임은 최소 1글자, 최대 10글자여야 합니다.")
    private String nickname;

    public static User toEntity(SignupRequestDto signupRequestDto, String userKey) {
        return User.builder()
                .email(signupRequestDto.getEmail())
                .name(signupRequestDto.getName())
                .nickname(signupRequestDto.getNickname())
                .userKey(userKey)
                .build();
    }
}
