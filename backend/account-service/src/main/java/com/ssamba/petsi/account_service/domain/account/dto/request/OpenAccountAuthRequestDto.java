package com.ssamba.petsi.account_service.domain.account.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OpenAccountAuthRequestDto {
	private String bankName;
	@NotNull
	private String accountNo;
}
