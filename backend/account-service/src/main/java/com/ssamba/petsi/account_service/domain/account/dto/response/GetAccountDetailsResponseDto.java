package com.ssamba.petsi.account_service.domain.account.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.global.dto.PetCustomDto;
import com.ssamba.petsi.account_service.global.dto.PetResponseDto;

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
	@Setter
	private Long balance;
	@Setter
	private int createDdays;
	@Setter
	private int expireDdays;
	private Double currentMonthlyInterest;
	private LocalDate expireDate;
	@Setter
	List<PetCustomDto> petList;
	@Setter
	private List<GetAccountHistoryResponseDto> transactionHistory;

	public GetAccountDetailsResponseDto(Account account) {
		this.accountId = account.getAccountId();
		this.accountName = account.getName();
		this.accountNo = account.getAccountNo();
		this.currentMonthlyInterest = account.getInterestRate();
		this.expireDate = account.getMaturityDate();
	}
}
