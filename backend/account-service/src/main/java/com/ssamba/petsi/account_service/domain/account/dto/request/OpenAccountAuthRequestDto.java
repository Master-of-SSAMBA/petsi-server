package com.ssamba.petsi.account_service.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OpenAccountAuthRequestDto {
	private String bankName;
	private String accountNo;
}
