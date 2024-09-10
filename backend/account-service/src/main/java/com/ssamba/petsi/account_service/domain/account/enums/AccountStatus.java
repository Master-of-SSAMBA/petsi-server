package com.ssamba.petsi.account_service.domain.account.enums;

import lombok.Getter;

@Getter
public enum AccountStatus {
	ACTIVATED("활성"),
	INACTIVATED("비활성");

	private final String value;

	AccountStatus(String value) {
		this.value = value;
	}
}
