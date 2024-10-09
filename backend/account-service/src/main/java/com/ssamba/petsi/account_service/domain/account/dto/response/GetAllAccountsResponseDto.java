package com.ssamba.petsi.account_service.domain.account.dto.response;

import java.util.List;

import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.global.dto.PetCustomDto;
import com.ssamba.petsi.account_service.global.dto.PetResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetAllAccountsResponseDto {

	private int pictureCnt;
	private double interest;

	private List<AccountDto> accounts;

	@Getter
	@AllArgsConstructor
	public static class AccountDto {
		private Long accountId;
		private String accountNo;
		private String name;
		@Setter
		List<PetCustomDto> petList;
		@Setter
		private Long balance;
		private Double interestRate;
		private String productName;
		private String status;


		private AccountDto(Account account, List<PetCustomDto> petList) {
			this.accountId = account.getAccountId();
			this.accountNo = account.getAccountNo();
			this.name = account.getName();
			this.petList = petList;
			this.balance = account.getBalance();
			this.interestRate = account.getInterestRate();
			this.productName = account.getAccountProduct().getTitle();
			this.status = account.getStatus();
		}

		public static AccountDto from(Account account) {
			return new AccountDto(account, null);
		}
	}

}
