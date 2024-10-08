package com.ssamba.petsi.account_service.global.dto;

import com.ssamba.petsi.account_service.domain.account.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationProducerDto {
	private Long userId;
	private String category;
	private String content;
	private Long id;

	public static NotificationProducerDto toNoticeProducerDto(Account account) {
		return new NotificationProducerDto(
			account.getUserId(),
			"bank",
			account.getName() + " 계좌에 " + account.getRecurringTransaction().getAmount() + "원이 입금되었어요.",
			account.getAccountId()
		);
	}


}
