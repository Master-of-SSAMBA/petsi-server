package com.ssamba.petsi.expense_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	INTERNAL_SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다."),
	INVALID_DATA_FORMAT(401, "올바르지 않은 데이터 형식입니다.");


	private final int status;
	private final String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}