package com.ssamba.petsi.account_service.domain.account.dto.fin;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinApiResponseDto<T> {
	@JsonProperty("Header")
	private FinApiHeaderResponseDto header;

	@JsonProperty("REC")
	private T rec;

	@Getter
	public static class CreateAccountResponseDto {
		private String bankCode;
		private String accountNo;
		private CurrencyResponseDto currency;
	}

	public static class CurrencyResponseDto {
		private String currency;
		private String currencyName;
	}
}
