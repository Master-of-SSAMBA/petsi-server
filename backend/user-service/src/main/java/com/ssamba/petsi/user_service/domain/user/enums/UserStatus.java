package com.ssamba.petsi.user_service.domain.user.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
	ACTIVATED("활성"),
	INACTIVATED("비활성");

	private final String value;

	UserStatus(String value) {
		this.value = value;
	}
}
