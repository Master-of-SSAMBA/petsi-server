package com.ssamba.petsi.user_service.global.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {

	private final ExceptionCode code;

	public BusinessLogicException(ExceptionCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
