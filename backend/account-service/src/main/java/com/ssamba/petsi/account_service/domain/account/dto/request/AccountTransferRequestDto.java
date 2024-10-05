package com.ssamba.petsi.account_service.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountTransferRequestDto {
	private Long accountId;
	private String destinationAccountNo;
	private Long amount;
	private String description;
	private String destinationDescription;
	private String password;
}
