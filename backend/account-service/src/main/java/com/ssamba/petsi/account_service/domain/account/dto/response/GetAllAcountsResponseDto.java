package com.ssamba.petsi.account_service.domain.account.dto.response;

import com.ssamba.petsi.account_service.domain.account.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetAllAcountsResponseDto {
	private Long accountId;
	private String accountNo;
	private String name;
	private String petPicture;
	private Long balance;
	private Double interestRate;
	private String productName;
	private String status;

	private GetAllAcountsResponseDto(Account account, String petPicture) {
		this.accountId = account.getAccountId();
		this.accountNo = account.getAccountNo();
		this.name = account.getName();
		this.petPicture = petPicture;
		this.balance = account.getBalance();
		this.interestRate = account.getInterestRate();
		this.productName = account.getAccountProduct().getTitle();
		this.status = account.getStatus();
	}

	public static GetAllAcountsResponseDto from(Account account) {
		return new GetAllAcountsResponseDto(account, null);
	}
}
