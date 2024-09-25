package com.ssamba.petsi.expense_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	INTERNAL_SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다."),
	INVALID_DATA_FORMAT(400, "올바르지 않은 데이터 형식입니다."),
	INVALID_FILE_FORM(400, "올바르지 않은 파일 형식입니다."),
	PURCHASE_NOT_FOUND(404, "존재하지 않는 구매 내역입니다."),
	PURCHASE_USER_NOT_MATCH(401, "구매 내역과 로그인한 유저 정보가 일치히지 않습니다"),
	MEDICAL_EXPENSE_NOT_FOUND(404, "존재하지 않는 의료비 내역입니다."),
	MEDICAL_EXPENSE_USER_NOT_MATCH(401, "의료비 내역과 로그인한 유저 정보가 일치히지 않습니다");

	private final int status;
	private final String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}