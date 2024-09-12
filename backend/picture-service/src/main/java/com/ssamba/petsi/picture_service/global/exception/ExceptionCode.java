package com.ssamba.petsi.picture_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    INTERNAL_SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다."),
    INVALID_FILE_FORM(400, "올바르지 않은 파일 형식입니다."),
    PICTURE_ALREADY_EXIST(401, "오늘 이미 인증을 완료했습니다."),
    PICTURE_USER_NOT_MATCH(401, "사진 등록자와 로그인한 유저 정보가 일치히지 않습니다."),
    PICTURE_NOT_FOUND(404, "해당하는 id의 사진이 없습니다.");

    private final int status;
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
