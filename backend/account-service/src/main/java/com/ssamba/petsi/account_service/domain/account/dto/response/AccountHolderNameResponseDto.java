package com.ssamba.petsi.account_service.domain.account.dto.response;

import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountHolderNameResponseDto {
	private String accountNo;
	private String bankName;
	private String name;

	public AccountHolderNameResponseDto(FinApiResponseDto.InquireDemandDepositAccountHolderName dto) {
		this.accountNo = dto.getAccountNo();
		this.bankName = dto.getBankName();
		this.name = dto.getUserName();
	}
}
