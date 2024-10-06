package com.ssamba.petsi.user_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	INTERNAL_SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다."),
	DUPLICATED_EMAIL(400, "이미 가입된 이메일입니다!"),
	DUPLICATED_NICKNAME(400, "이미 가입된 닉네임이 있습니다!"),
	KEYCLOAK_REGISTER_ERROR(500, "키클록 유저 등록에 실패했습니다!"),
	FINAPI_REGISTER_ERROR(500, "금융API 유저 등록에 실패했습니다!"),
	EMAIL_SENDING_ERROR(500, "인증 이메일 전송에 실패했습니다!")
	;

	private final int status;
	private final String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}