package com.ssamba.petsi.account_service.domain.account.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@NotNull
public class UpdateAccountNameRequestDto {
	private Long accountId;
	private String name;
}
