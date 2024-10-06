package com.ssamba.petsi.user_service.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CheckEmailRequestDto {
    @NotBlank(message = "이메일을 입력하세요!")
    // 이메일 형식 아이디@도메인이름.최상위도메인
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,50}$", message = "유효하지 않은 이메일입니다!")
    private String email;
}
