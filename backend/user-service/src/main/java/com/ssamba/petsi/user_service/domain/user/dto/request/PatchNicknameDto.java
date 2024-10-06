package com.ssamba.petsi.user_service.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatchNicknameDto {
    @NotBlank(message = "닉네임을 입력하세요!")
    @Size(min = 1, max = 10, message = "닉네임은 최소 1글자, 최대 10글자여야 합니다.")
    private String nickname;
}
