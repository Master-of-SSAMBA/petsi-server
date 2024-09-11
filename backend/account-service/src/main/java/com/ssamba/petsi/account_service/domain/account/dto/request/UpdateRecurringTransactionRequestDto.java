package com.ssamba.petsi.account_service.domain.account.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecurringTransactionRequestDto {
	private Long accountId;
	@Min(1000)
	@Max(3000000)
	private int amount;

	@Min(1)
	@Max(31)
	private int day;
}
