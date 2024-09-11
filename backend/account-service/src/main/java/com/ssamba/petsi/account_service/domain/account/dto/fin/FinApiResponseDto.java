package com.ssamba.petsi.account_service.domain.account.dto.fin;

import java.util.List;

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

		static class CurrencyResponseDto {
			private String currency;
			private String currencyName;
		}
	}

	@Getter
	public static class TransactionHistoryResponseDto {
		private String totalCount;
		private List<Transaction> list;

		public static class Transaction {
			private String transactionUniqueNo;
			private String transactionDate;
			private String transactionTime;
			private String transactionType;
			private String transactionTypeName;
			private String transactionAccountNo;
			private String transactionBalance;
			private String transactionAfterBalance;
			private String transactionSummary;
			private String transactionMemo;
		}

	}
}
