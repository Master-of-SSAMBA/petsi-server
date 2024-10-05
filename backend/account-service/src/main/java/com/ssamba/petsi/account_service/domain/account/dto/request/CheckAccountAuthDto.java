package com.ssamba.petsi.account_service.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Deprecated
@Getter
@AllArgsConstructor
public class CheckAccountAuthDto {
	@Setter
	private String userKey;
	private String accountNo;
	private String bankName;
	private String code;

	public static CheckAccountAuthDto toDto(CreateAccountRequestDto dto, String userKey) {
		return new CheckAccountAuthDto(userKey, dto.getAccountNo(), dto.getBankName(), dto.getCode());
	}
}
