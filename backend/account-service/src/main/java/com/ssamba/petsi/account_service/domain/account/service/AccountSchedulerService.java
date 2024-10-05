package com.ssamba.petsi.account_service.domain.account.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AccountSchedulerService {

	//todo: 매일 적금 자동이체 확인 후 이체
	@Scheduled(cron = "0 0 8 * * *")
	void autoTransfer() {

	}

	//todo: 매월 1일 지난 달 이자율 확인 후 갱신

	//todo: 매월 10일 이자 지급
}
