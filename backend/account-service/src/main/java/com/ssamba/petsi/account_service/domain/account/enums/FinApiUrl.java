package com.ssamba.petsi.account_service.domain.account.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum FinApiUrl {
	openAccountAuth("https://finopenapi.ssafy.io/ssafy/api/v1/edu/accountAuth/openAccountAuth");
	private String url;

	FinApiUrl(String url) {
		this.url = url;
	}
}
