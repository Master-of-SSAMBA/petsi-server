package com.ssamba.petsi.account_service.domain.account.dto.response;

import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiResponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAccountHistoryResponseDto {
	private Long transactionUniqueNo;
	private DateResponseDto transactionDate;
	private String title;
	private String transactionType;
	private Long transactionAmount;
	private Long transactionAfterBalance;
	private String transactionMemo;

	public static GetAccountHistoryResponseDto from(FinApiResponseDto.TransactionHistoryResponseDto.Transaction transaction) {

		String date = transaction.getTransactionDate();
		String formattedDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);

		String time = transaction.getTransactionTime();
		String formattedTime = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);


		GetAccountHistoryResponseDto dto = GetAccountHistoryResponseDto.builder()
			.transactionUniqueNo(transaction.getTransactionUniqueNo())
			.transactionDate(new DateResponseDto(formattedDate, formattedTime))
			.title(transaction.getTransactionSummary())
			.transactionType(transaction.getTransactionType().equals("1") ? "입금":"출금")
			.transactionAmount(transaction.getTransactionBalance())
			.transactionAfterBalance(transaction.getTransactionAfterBalance())
			.transactionMemo(transaction.getTransactionMemo())
			.build();

		return dto;
	}
}
