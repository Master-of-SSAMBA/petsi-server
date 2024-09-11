package com.ssamba.petsi.account_service.domain.account.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.ssamba.petsi.account_service.domain.account.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountDetailsResponseDto {
	private Long accountId;
	private String accountName;
	private String accountNo;
	private Long balance;
	@Setter
	private int createDdays;
	@Setter
	private int expireDdays;
	private Double currentMonthlyInterest;
	private LocalDate expireDate;
	@Setter
	private List<GetAccountHistoryResponseDto> transactionHistory;

	public GetAccountDetailsResponseDto(Account account) {
		this.accountId = account.getAccountId();
		this.accountName = account.getName();
		this.accountNo = account.getAccountNo();
		this.balance = account.getBalance();
		this.currentMonthlyInterest = account.getInterestRate();
		this.expireDate = account.getMaturityDate();
	}
}
