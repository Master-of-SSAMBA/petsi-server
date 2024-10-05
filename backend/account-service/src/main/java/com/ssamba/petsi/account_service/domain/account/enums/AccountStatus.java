package com.ssamba.petsi.account_service.domain.account.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatus {
	ACTIVATED("활성"),
	INACTIVATED("비활성"),
	EXPIRED("만기");

	private final String value;

}
