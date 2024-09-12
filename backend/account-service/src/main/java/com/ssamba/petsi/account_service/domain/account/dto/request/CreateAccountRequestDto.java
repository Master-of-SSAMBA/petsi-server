package com.ssamba.petsi.account_service.domain.account.dto.request;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.domain.account.entity.AccountProduct;
import com.ssamba.petsi.account_service.domain.account.entity.LinkedAccount;
import com.ssamba.petsi.account_service.domain.account.entity.RecurringTransaction;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAccountRequestDto {
	@NotNull
	@Min(1)
	private Long accountProductId;
	@NotNull
	@Min(1)
	@Max(31)
	private int nextTransactionDay;
	@Min(1000)
	@Max(3000000)
	private int amount;
	private List<Integer> pets;
	private String name;
	@NotNull
	@Length(min = 4, max = 4)
	private String password;
	private String accountNo;
	private String bankName;
	@Length(min = 4, max = 4)
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

	public static Account toAccount(CreateAccountRequestDto createAccountRequestDto, AccountProduct accountProduct,
		String accountNo, Long userId) {
		LocalDate date = LocalDate.now();
		date = date.plusYears(20);

		Account account = Account.builder()
			.accountNo(accountNo)
			.accountProduct(accountProduct)
			.password(createAccountRequestDto.getPassword())
			.maturityDate(date)
			.name(createAccountRequestDto.getName())
			.userId(userId)
			.build();

		return account;
	}

	public static RecurringTransaction toRecurringTransaction(CreateAccountRequestDto createAccountRequestDto,
		Account account) {
		int day = createAccountRequestDto.getNextTransactionDay();

		RecurringTransaction recurringTransaction = RecurringTransaction.builder()
			.amount(createAccountRequestDto.getAmount())
			.frequency(day)
			.account(account)
			.build();

		LocalDate currentDate = LocalDate.now();
		LocalDate nextTransactionDate;

		if (day >= currentDate.getDayOfMonth()) {
			nextTransactionDate = currentDate.withDayOfMonth(
				Math.min(day, currentDate.lengthOfMonth()));
		} else {
			nextTransactionDate = currentDate.plusMonths(1).withDayOfMonth(
				Math.min(day, currentDate.plusMonths(1).lengthOfMonth()));
		}
		recurringTransaction.setNextTransactionDate(nextTransactionDate);
		return recurringTransaction;
	}

}
