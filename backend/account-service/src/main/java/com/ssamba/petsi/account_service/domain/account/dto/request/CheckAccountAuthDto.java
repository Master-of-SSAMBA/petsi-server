package com.ssamba.petsi.account_service.domain.account.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CheckAccountAuthDto {
	@Setter
	private String userKey;
	private String accountNo;
	private String bankName;
	private String code;

	public CheckAccountAuthDto(CreateAccountRequestDto createAccountRequestDto) {
		this.accountNo = createAccountRequestDto.getAccountNo();
		this.bankName = createAccountRequestDto.getBankName();
		this.code = createAccountRequestDto.getCode();
	}
}
