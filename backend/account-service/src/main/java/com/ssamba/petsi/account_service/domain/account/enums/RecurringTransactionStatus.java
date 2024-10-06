package com.ssamba.petsi.account_service.domain.account.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecurringTransactionStatus {
	PENDING("이체 대기"),            // 아직 이번 달 적금 이체일이 오지 않았을 때
	UNCOMPLETED("미완료"),          // 이번 적금 이체일이 지났고, 이체가 완료되지 않았을 때
	COMPLETED("완료");              // 이번 적금 이체일이 지났고, 이번 달 자동이체가 완료됐을 때

	private final String value;

}
