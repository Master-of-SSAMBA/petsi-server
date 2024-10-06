package com.ssamba.petsi.account_service.domain.account.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecurringTransactionRequestDto {
	@NotNull
	private Long accountId;
	@Length(min = 1000, max = 3000000)
	private Long amount;
	@Length(min = 1, max = 31)
	private int day;
}
