package com.ssamba.petsi.user_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	INTERNAL_SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다."),
	DUPLICATED_EMAIL(400, "이미 가입된 이메일입니다!"),
	DUPLICATED_NICKNAME(400, "이미 가입된 닉네임이 있습니다!"),
	KEYCLOAK_REGISTER_ERROR(500, "키클록 유저 등록에 실패했습니다!"),
	FINAPI_REGISTER_ERROR(500, "금융API 유저 등록에 실패했습니다!"),
	EMAIL_SENDING_ERROR(500, "인증 이메일 전송에 실패했습니다!"),
	USER_NOT_FOUND(404, "유저 아이디에 해당하는 유저가 없습니다."),
	INVALID_FILE_FORM(400, "올바르지 않은 파일 형식입니다." ),
	LEAVE_USER(400, "탈퇴한 유저입니다." ),
	USER_DEACTIVATION_ERROR(500, "유저 비활성화에 실패했습니다!");

	private final int status;
	private final String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}