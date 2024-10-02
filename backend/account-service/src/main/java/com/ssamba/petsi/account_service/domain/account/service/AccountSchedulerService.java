package com.ssamba.petsi.account_service.domain.account.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AccountSchedulerService {

	@Scheduled(cron = "0 0 8 * * *")
	void autoTransfer() {

	}
}
