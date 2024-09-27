package com.ssamba.petsi.user_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	INTERNAL_SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다."),
	DUPLICATED_EMAIL(400, "이미 가입된 이메일입니다!"),
	DUPLICATED_NICKNAME(400, "이미 가입된 닉네임이 있습니다!"),
	;

	private final int status;
	private final String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}