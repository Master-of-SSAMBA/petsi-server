package com.ssamba.petsi.user_service.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ChangePasswordDto {
    @NotBlank(message = "비밀번호를 입력하세요!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=]).{8,20}$", message = "유효하지 않은 비밀번호입니다!")
    private String password;
}
