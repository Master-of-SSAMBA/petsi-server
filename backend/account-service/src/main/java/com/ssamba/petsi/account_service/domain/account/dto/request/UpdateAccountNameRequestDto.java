package com.ssamba.petsi.account_service.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountNameRequestDto {
	private Long accountId;
	private String name;
}
