package com.ssamba.petsi.account_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	INTERNAL_SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다."),
	INVALID_CODE(400, "인증 코드가 유효하지 않습니다."),
	ACCOUNT_NOT_FOUND(404, "계좌가 존재하지 않습니다."),
	INVALID_ACCOUNT_NO(400, "적절한 형태의 계좌번호를 입력하세요."),
	ACCOUNT_CATEGORY_NOT_FOUND(404, "계좌 종류가 존재하지 않습니다."),
	INVALID_NAME(400, "적절한 형태의 계좌명을 입력하세요."),
	INVALID_PASSWORD(400, "적절한 형태의 비밀번호를 입력하세요."),
	INVALID_AMOUNT(400, "적절한 형태의 자동 이체 금액을 입력하세요."),
	ACCOUNT_USER_NOT_MATCH(401, "계좌주와 로그인한 유저가 일치하지 않습니다."),
	INVALID_ACCOUNT_STATUS(400, "계좌가 활성된 상태가 아닙니다."),
	EMPTY_REQUEST(400, "변경할 사항이 없습니다.");

	private final int status;
	private final String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}