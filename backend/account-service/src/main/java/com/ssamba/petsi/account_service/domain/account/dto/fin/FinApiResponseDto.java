package com.ssamba.petsi.account_service.domain.account.dto.fin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinApiResponseDto<T> {
	private FinApiHeaderResponseDto Header;
	private T REC;  // 제네릭 타입으로 선언

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
