package com.ssamba.petsi.account_service.domain.account.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiResponseDto;
import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.domain.account.entity.RecurringTransaction;
import com.ssamba.petsi.account_service.domain.account.enums.AccountStatus;
import com.ssamba.petsi.account_service.domain.account.enums.RecurringTransactionStatus;
import com.ssamba.petsi.account_service.domain.account.repository.AccountRepository;
import com.ssamba.petsi.account_service.domain.account.repository.RecurringTransactionRepository;
import com.ssamba.petsi.account_service.global.client.PetClient;
import com.ssamba.petsi.account_service.global.client.PictureClient;
import com.ssamba.petsi.account_service.global.dto.PictureMonthlyRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountSchedulerService {

	private final RecurringTransactionRepository recurringTransactionRepository;
	private final AccountRepository accountRepository;
	private final PictureClient pictureClient;
	private final AccountFinApiService accountFinApiService;

	//todo: 매일 적금 자동이체 확인 후 이체
	@Scheduled(cron = "0 0 8 * * *")
	void autoTransfer() {
		List<RecurringTransaction> accounts = recurringTransactionRepository
			.findAllByStatusNotAndNextTransactionDateLessThanEqual(RecurringTransactionStatus.COMPLETED.getValue(),
				LocalDate.now());

		accounts.forEach(account -> {
			try {
				//todo: FinApi 호출하여 적금 자동이체
			} catch (Exception e) {
				account.setStatus(RecurringTransactionStatus.UNCOMPLETED.getValue());
			}

			account.setStatus(RecurringTransactionStatus.COMPLETED.getValue());

		});

	}

	//todo: 매월 1일 지난 달 이자율 확인 후 갱신 및 잔액 갱신
	@Scheduled(cron = "0 0 0 1 * *")
	void updateAccountPerMonth() {
		List<Account> accounts = accountRepository.findAllByStatus(AccountStatus.ACTIVATED.getValue());
		accounts.forEach(account -> {
			PictureMonthlyRequestDto req = new PictureMonthlyRequestDto(
				account.getUserId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue());
			int pictureCnt = pictureClient.getMonthlyPicture(req).size();
			account.setInterestRate(Math.min(1+pictureCnt*0.1, 3));
			accountRepository.save(account);
		});
	}

	//todo: 매월 1일 자동이체 날짜 갱신
	@Scheduled(cron = "0 0 0 1 * *")
	void updateRecurringTransactionPerMonth() {
		List<RecurringTransaction> accounts = recurringTransactionRepository.findAll();
		accounts.forEach(account -> {
			int day = LocalDate.now().lengthOfMonth();
			LocalDate nextTransactionDate = LocalDate.now().withDayOfMonth(day);
			if(day > account.getPaymentDate()) {
				nextTransactionDate = nextTransactionDate.withDayOfMonth(account.getPaymentDate());
			}
			account.setNextTransactionDate(nextTransactionDate);
			account.setStatus(RecurringTransactionStatus.PENDING.getValue());
			recurringTransactionRepository.save(account);
		});

	}

	//todo: 매월 10일 이자 지급
	@Scheduled(cron = "0 0 1 10 * *")
	void addInterest() {
		LocalDate date = LocalDate.now().minusMonths(1);
		String startDate = date.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String endDate = date.withDayOfMonth(date.lengthOfMonth()).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		List<Account> accounts = accountRepository.findAllByStatus(AccountStatus.ACTIVATED.getValue());
		accounts.forEach(account -> {
			double interest = account.getInterestRate();
			//todo: userClient에서 userKey 값 가져오기
			FinApiResponseDto.TransactionHistoryResponseDto returnValue =
				accountFinApiService.inquireTransactionHistoryList(account, startDate, endDate, "");

			//만약 cnt가 0이면, 그 전달까지 endDate로 잡고 balance값 다 적용


			//그래도 데이터가 없다면 이자 지급 X

			//아니라면, 계산

		});
	}
}
