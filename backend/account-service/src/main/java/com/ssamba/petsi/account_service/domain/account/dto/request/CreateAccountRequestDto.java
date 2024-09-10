package com.ssamba.petsi.account_service.domain.account.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.domain.account.entity.AccountProduct;
import com.ssamba.petsi.account_service.domain.account.entity.LinkedAccount;
import com.ssamba.petsi.account_service.domain.account.entity.RecurringTransaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAccountRequestDto {
	private Long accountProductId;
	private int nextTransactionDay;
	private int amount;
	private List<Integer> pets;
	private String name;
	private String password;
	private String accountNo;
	private String bankName;
	private String code;
	private Boolean isAuto;


	public static LinkedAccount toLinkedAccount(CreateAccountRequestDto createAccountRequestDto, Account account) {
		LinkedAccount linkedAccount = LinkedAccount.builder()
			.account(account)
			.accountNumber(createAccountRequestDto.getAccountNo())
			.bankName(createAccountRequestDto.getBankName())
			.build();

		return linkedAccount;
	}

	public static Account toAccount(CreateAccountRequestDto createAccountRequestDto, AccountProduct accountProduct, String accountNo, Long userId) {
		LocalDate date = LocalDate.now();
		date = date.plusYears(20);

		Account account = Account.builder()
			.accountNo(accountNo)
			.password(createAccountRequestDto.getPassword())
			.maturityDate(date)
			.name(createAccountRequestDto.getName())
			.userId(userId)
			.build();

		return account;
	}

	public static RecurringTransaction toRecurringTransaction(CreateAccountRequestDto createAccountRequestDto, Account account) {
		int day = createAccountRequestDto.getNextTransactionDay();
		LocalDate currentDate = LocalDate.now();
		LocalDate nextMonthDate = currentDate.plusMonths(1).withDayOfMonth(Math.min(day, currentDate.plusMonths(1).lengthOfMonth()));

		RecurringTransaction recurringTransaction = RecurringTransaction.builder()
			.amount(createAccountRequestDto.getAmount())
			.frequency(day)
			.nextTransactionDate(nextMonthDate)
			.account(account)
			.build();
		return recurringTransaction;
	}

}
