package com.ssamba.petsi.account_service.domain.account.dto.request;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

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
	private Long accountProductId;
	private List<Long> pets;
	private String name;
	@NotNull
	@Length(min = 4, max = 4)
	private String password;

	@NotNull
	private Boolean isAuto;

	private String accountNo;
	@Min(1)
	@Max(31)
	private int nextTransactionDay;
	@Min(10000)
	@Max(3000000)
	private Long amount;


	public static LinkedAccount toLinkedAccount(CreateAccountRequestDto createAccountRequestDto, Account account) {

		return LinkedAccount.builder()
			.account(account)
			.accountNumber("9991988005402710")
			.bankName("싸피은행")
			.build();
	}

	public static Account toAccount(CreateAccountRequestDto dto, String accountNo, Long userId, String userKey, AccountProduct product) {
		return new Account(product, userId, userKey, accountNo, dto.getName(), dto.getPassword());
	}

	public static RecurringTransaction toRecurringTransaction(CreateAccountRequestDto createAccountRequestDto,
		Account account) {
		int day = createAccountRequestDto.getNextTransactionDay();

		RecurringTransaction recurringTransaction = RecurringTransaction.builder()
			.amount(createAccountRequestDto.getAmount())
			.paymentDate(day)
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
