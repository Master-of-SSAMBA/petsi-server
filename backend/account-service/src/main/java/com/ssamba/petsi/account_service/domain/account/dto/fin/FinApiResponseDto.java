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

		@Getter
		public static class Transaction {
			private Long transactionUniqueNo;
			private String transactionDate;
			private String transactionTime;
			private String transactionType;
			private String transactionTypeName;
			private String transactionAccountNo;
			private Long transactionBalance;
			private Long transactionAfterBalance;
			private String transactionSummary;
			private String transactionMemo;
		}

	}

	@Getter
	public static class AccountListResponseDto {
		private String bankCode;
		private String bankName;
		private String userName;
		private String accountNo;
		private String accountName;
		private String accountTypeCode;
		private String accountTypeName;
		private String accountCreateDate;
		private String accountExpiryDate;
		private String dailyTransferLimit;
		private String oneTimeTransferLimit;
		private Long accountBalance;
		private String lastTransactionDate;
		private String currency;
	}
}
