package com.ssamba.petsi.pet_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    INTERNAL_SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다."),
    INVALID_FILE_FORM(400, "올바르지 않은 파일 형식입니다."),
    PET_NOT_FOUND(404, "반려동물이 존재하지 않습니다."),
    PET_USER_NOT_MATCH(401, "반려동물과 로그인 한 유저가 일치하지 않습니다.");

    private final int status;
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
