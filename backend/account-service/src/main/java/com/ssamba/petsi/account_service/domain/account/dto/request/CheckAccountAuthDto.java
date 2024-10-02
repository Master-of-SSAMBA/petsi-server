package com.ssamba.petsi.account_service.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class CheckAccountAuthDto {
	@Setter
	private String userKey;
	private String accountNo;
	private String bankName;
	private String code;

	public static CheckAccountAuthDto fromCreateAccountDto(CreateAccountRequestDto dto, String userKey) {
		return CheckAccountAuthDto.builder()
			.userKey(userKey)
			.accountNo(dto.getAccountNo())
			.bankName(dto.getBankName())
			.code(dto.getCode())
			.build();
	}
}
