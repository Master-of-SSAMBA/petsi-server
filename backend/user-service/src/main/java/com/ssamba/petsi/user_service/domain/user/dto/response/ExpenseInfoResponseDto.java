package com.ssamba.petsi.user_service.domain.user.dto.response;

import lombok.Getter;

@Getter
public class ExpenseInfoResponseDto {
    private String nickname;
    private String img;

    public ExpenseInfoResponseDto (String nickname, String img) {
        this.nickname = nickname;
        this.img = img;
    }
}
