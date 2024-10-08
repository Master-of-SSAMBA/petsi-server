package com.ssamba.petsi.account_service.global.dto;

import com.ssamba.petsi.account_service.domain.account.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationProducerDto<T> {
	private Long userId;
	private T content;
	private Long id;

	public static NotificationProducerDto<AccountNoticeInfo> toNoticeProducerDto(Account account) {
		return new NotificationProducerDto<AccountNoticeInfo>(
			account.getUserId(),
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
