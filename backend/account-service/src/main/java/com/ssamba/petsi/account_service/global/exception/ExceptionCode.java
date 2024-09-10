package com.ssamba.petsi.account_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
	private final int status;
	private final String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}