package com.ssamba.petsi.account_service.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountTransferRequestDto {
	public Long accountId;
	public String destinationAccountNo;
	public Long amount;
	public String description;
	public String destinationDescription;
}
