package com.ssamba.petsi.account_service.domain.account.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@NotNull
public class CheckAccountPassword {
	private Long accountId;
	private String accountNo;
	private String password;
}
