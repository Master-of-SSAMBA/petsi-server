package com.ssamba.petsi.account_service.domain.account.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountTransferRequestDto {
	@NotNull
	private Long accountId;
	@NotNull
	private String destinationAccountNo;
	@NotNull
	private Long amount;
	@NotNull
	private String description;
	private String destinationDescription;
	@Length(min = 4, max = 4)
	private String password;
}
