package com.ssamba.petsi.account_service.domain.account.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountNameRequestDto {
	@NotNull
	private Long accountId;
	@NotNull
	private String name;
}
