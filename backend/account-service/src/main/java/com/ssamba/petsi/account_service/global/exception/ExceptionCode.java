package com.ssamba.petsi.account_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	TEST_EXCEPTION(1, "test");


	private final int status;
	private final String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}