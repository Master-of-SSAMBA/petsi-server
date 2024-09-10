package com.ssamba.petsi.account_service.domain.account.dto.fin;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinApiResponseDto<T> {
	@JsonProperty("Header")
	private FinApiHeaderResponseDto header;  // Use lowercase 'header' to match naming convention

	@JsonProperty("REC")
	private T rec;  // Make sure to use "rec" for the field to match the JSON key

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
