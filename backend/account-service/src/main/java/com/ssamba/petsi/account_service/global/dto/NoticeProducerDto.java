package com.ssamba.petsi.account_service.global.dto;

import com.ssamba.petsi.account_service.domain.account.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeProducerDto<T> {
	private Long userId;
	private String category;
	private T content;
	private Long id;

	public static NoticeProducerDto<AccountNoticeInfo> toNoticeProducerDto(Account account) {
		return new NoticeProducerDto<AccountNoticeInfo>(
			account.getUserId(),
			"transfer-success",
			new AccountNoticeInfo(
				account.getName(),
				account.getRecurringTransaction().getAmount()),
			account.getAccountId());
	}

	@AllArgsConstructor
	public static class AccountNoticeInfo {
		private String accountName;
		private Long amount;
	}

}
