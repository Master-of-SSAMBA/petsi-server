package com.ssamba.petsi.user_service.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
	ACTIVATED("활성"),
	INACTIVATED("비활성");

	private final String value;

	public static UserStatus getUserStatus(boolean value) {
		return value ? ACTIVATED : INACTIVATED;
	}
}
