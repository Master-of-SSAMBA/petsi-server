package com.ssamba.petsi.schedule_service.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	INTERNAL_SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다."),
	DUPLICATED_SCHEDULE_CATEGORY(400, "이미 존재하는 일정 카테고리입니다."),
	SCHEDULE_USER_NOT_MATCH(401, "일정 값과 로그인한 유저 정보가 일치히지 않습니다."),
	SCHEDULE_CATEGORY_NOT_FOUND(404, "존재하지 않는 일정 카테고리입니다."),
	SCHEDULE_NOT_FOUND(404, "존재하지 않는 일정입니다");

	private final int status;
	private final String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}