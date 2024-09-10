package com.ssamba.petsi.account_service.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckAccountAuthRequestDto {
	private String bankName;
	private String accountNo;
	private String code;
}
