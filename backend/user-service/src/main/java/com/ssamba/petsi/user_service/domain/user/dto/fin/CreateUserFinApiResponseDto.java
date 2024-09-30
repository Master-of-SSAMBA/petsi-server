package com.ssamba.petsi.user_service.domain.user.dto.fin;

import lombok.Getter;

@Getter
public class CreateUserFinApiResponseDto {
    private String userId;
    private String userName;
    private String institutionCode;
    private String userKey;
    private String created;
    private String modified;
}
