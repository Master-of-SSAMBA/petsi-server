package com.ssamba.petsi.account_service.domain.account.dto.fin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssamba.petsi.account_service.domain.account.enums.FinApiUrl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FinApiRequestDto<T> {
	@JsonProperty("Header")
	private FinApiHeaderRequestDto header;

	@JsonProperty("REC")
	private T rec;


	@Getter
	@AllArgsConstructor
	public static class OpenAccountAuthRequestDto {
		private String accountNo;
		private final String authText="petsi";

		public static FinApiRequestDto<OpenAccountAuthRequestDto> toOpenAccountAuth(String userKey, String accountNo) {
			FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.openAccountAuth.name(),
				FinApiUrl.openAccountAuth.name(), userKey);

			return new FinApiRequestDto<>(header, new OpenAccountAuthRequestDto(accountNo));
		}

	}


	@Getter
	@AllArgsConstructor
	public static class CheckAuthCodeRequestDto {
		private String accountNo;
		private final String authText="petsi";
		private String authCode;

		public static FinApiRequestDto<CheckAuthCodeRequestDto> toCheckAuthCode(String userKey,
			String accountNo, String authCode) {
			FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.checkAuthCode.name(),
				FinApiUrl.checkAuthCode.name(), userKey);

			return new FinApiRequestDto<>(header, new CheckAuthCodeRequestDto(accountNo, authCode));
		}

	}

	@Getter
	@AllArgsConstructor
	public static class CreateAccountRequestDto {
		private String accountTypeUniqueNo;

		public static FinApiRequestDto<CreateAccountRequestDto> toCreateAccount(String userKey,
			String accountTypeUniqueNo) {
			FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.createDemandDepositAccount.name(),
				FinApiUrl.createDemandDepositAccount.name(), userKey);

			return new FinApiRequestDto<>(header, new CreateAccountRequestDto(accountTypeUniqueNo));
		}
	}

	@Getter
	@AllArgsConstructor
	public static class DeleteAccountRequestDto {
		private String accountNo;
		private String refundAccountNo;

		public static FinApiRequestDto<DeleteAccountRequestDto> toDeleteAccount(String userKey,
			String accountNo, String refundAccountNo) {
			FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.deleteDemandDepositAccount.name(),
				FinApiUrl.deleteDemandDepositAccount.name(), userKey);

			return new FinApiRequestDto<>(header, new DeleteAccountRequestDto(accountNo, refundAccountNo));
		}
	}

	@Getter
	@AllArgsConstructor
	public static class InquireAccount {
		private String accountNo;

		public static FinApiRequestDto<InquireAccount> toInquireAccount(String userKey,
			String accountNo) {
			FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.inquireDemandDepositAccount.name(),
				FinApiUrl.inquireDemandDepositAccount.name(), userKey);

			return new FinApiRequestDto<>(header, new InquireAccount(accountNo));
		}
	}

	@Getter
	@AllArgsConstructor
	public static class TransactionHistory {
		private String accountNo;
		private String startDate;
		private String endDate;
		private String transactionType;
		private String orderByType;

		public static FinApiRequestDto<TransactionHistory> toHistoryList(String userKey,
			String accountNo, String startDate, String endDate) {
			FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.inquireTransactionHistoryList.name(),
				FinApiUrl.inquireTransactionHistoryList.name(), userKey);

			return new FinApiRequestDto<>(header, new TransactionHistory(accountNo, startDate, endDate,
				"A", "DESC"));
		}
	}

	@Getter
	@AllArgsConstructor
	public static class UpdateTransfer {
		private String depositAccountNo;
		private String depositTransactionSummary;
		private Long transactionBalance;
		private String withdrawalAccountNo;
		private String withdrawalTransactionSummary;

		public static FinApiRequestDto<UpdateTransfer> toUpdateTransfer(String userKey,
			String depositAccountNo, String depositTransactionSummary, Long transactionBalance,
			String withdrawalAccountNo, String withdrawalTransactionSummary) {
			FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.updateDemandDepositAccountTransfer.name(),
				FinApiUrl.updateDemandDepositAccountTransfer.name(), userKey);

			return new FinApiRequestDto<>(header, new UpdateTransfer(depositAccountNo, depositTransactionSummary, transactionBalance,
				withdrawalAccountNo, withdrawalTransactionSummary));
		}
	}
}
