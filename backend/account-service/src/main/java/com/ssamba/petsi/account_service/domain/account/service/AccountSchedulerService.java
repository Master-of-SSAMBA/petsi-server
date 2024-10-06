package com.ssamba.petsi.account_service.domain.account.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiResponseDto;
import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.domain.account.entity.RecurringTransaction;
import com.ssamba.petsi.account_service.domain.account.enums.AccountStatus;
import com.ssamba.petsi.account_service.domain.account.enums.RecurringTransactionStatus;
import com.ssamba.petsi.account_service.domain.account.repository.AccountRepository;
import com.ssamba.petsi.account_service.domain.account.repository.RecurringTransactionRepository;
import com.ssamba.petsi.account_service.global.client.PictureClient;
import com.ssamba.petsi.account_service.global.dto.PictureMonthlyRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountSchedulerService {

	private final RecurringTransactionRepository recurringTransactionRepository;
	private final AccountRepository accountRepository;
	private final PictureClient pictureClient;
	private final AccountFinApiService accountFinApiService;

	@Retryable(
		value = { Exception.class },
		maxAttempts = 20,
		backoff = @Backoff(delay = 10000)
	)
	@Scheduled(cron = "0 0 8 * * *")
	void autoTransfer() { //매일 오전 8시 적금 자동이체
		List<RecurringTransaction> accounts = recurringTransactionRepository
			.findAllByStatusNotAndNextTransactionDateLessThanEqual(RecurringTransactionStatus.COMPLETED.getValue(),
				LocalDate.now());

		accounts.forEach(account -> {
			try {
				accountFinApiService.addBalance(account.getAccount(), account.getAmount(),
					account.getAccount().getLinkedAccount().getAccountNumber());
			} catch (Exception e) {
				account.setStatus(RecurringTransactionStatus.UNCOMPLETED.getValue());
			}

			account.setStatus(RecurringTransactionStatus.COMPLETED.getValue());

		});

	}

	@Retryable(
		value = { Exception.class },
		maxAttempts = 20,
		backoff = @Backoff(delay = 10000)
	)
	@Scheduled(cron = "0 0 0 1 * *")
	void updateAccountPerMonth() { //매월 1일 지난 달 이자율 확인 후 갱신
		List<Account> accounts = accountRepository.findAllByStatus(AccountStatus.ACTIVATED.getValue());
		accounts.forEach(account -> {
			PictureMonthlyRequestDto req = new PictureMonthlyRequestDto(
				account.getUserId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue());
			int pictureCnt = pictureClient.getMonthlyPicture(req).size();
			account.setInterestRate(Math.min(1+pictureCnt*0.1, 3));
			accountRepository.save(account);
		});
	}

	@Retryable(
		value = { Exception.class },
		maxAttempts = 20,
		backoff = @Backoff(delay = 10000)
	)
	@Scheduled(cron = "0 0 0 1 * *")
	void updateRecurringTransactionPerMonth() {//todo: 매월 1일 자동이체 날짜 갱신
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

	@Retryable(
		value = { Exception.class },
		maxAttempts = 20,
		backoff = @Backoff(delay = 10000)
	)
	@Scheduled(cron = "0 0 1 10 * *")
	void addInterest() {//todo: 매월 10일 이자 지급
		LocalDate date = LocalDate.now().minusMonths(1);
		String startDate = date.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String endDate = date.withDayOfMonth(date.lengthOfMonth()).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		List<Account> accounts = accountRepository.findAllByStatus(AccountStatus.ACTIVATED.getValue());

		accounts.forEach(account -> {
			double interestRate = account.getInterestRate();
			FinApiResponseDto.TransactionHistoryResponseDto transactionHistory =
				accountFinApiService.inquireTransactionHistoryList(account, startDate, endDate, account.getUserKey());

			// 거래 내역이 없을 때 처리
			if (transactionHistory.getList().isEmpty()) {
				transactionHistory = handleNoTransactionHistory(account, date);
			}

			Long calculatedInterest = calculateInterest(transactionHistory, account, date);
			if (calculatedInterest != 0) {
				accountFinApiService.addBalance(account, calculatedInterest, null);
			}
		});
	}

	// 거래 내역이 없을 때, 전체 거래 내역을 조회하는 로직
	private FinApiResponseDto.TransactionHistoryResponseDto handleNoTransactionHistory(Account account, LocalDate date) {
		String newEndDate = date.minusMonths(1).withDayOfMonth(date.minusMonths(1).lengthOfMonth())
			.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		return accountFinApiService.inquireTransactionHistoryList(account, "20200101", newEndDate, account.getUserKey());
	}

	// 이자 계산 로직을 별도로 추출
	private Long calculateInterest(FinApiResponseDto.TransactionHistoryResponseDto returnValue, Account account, LocalDate date) {
		List<FinApiResponseDto.TransactionHistoryResponseDto.Transaction> transactions = returnValue.getList();
		Long weightedBalanceSum = 0L;
		int previousDay = 1;

		// 거래 내역 처리
		for (int t = transactions.size() - 1; t >= 0; t--) {
			FinApiResponseDto.TransactionHistoryResponseDto.Transaction transaction = transactions.get(t);
			int transactionDay = Integer.parseInt(transaction.getTransactionDate().substring(6));
			Long adjustedBalance = adjustBalance(transaction);
			weightedBalanceSum += adjustedBalance * (transactionDay - previousDay);
			previousDay = transactionDay;
		}

		// 마지막 거래일 이후 날짜 처리
		if (previousDay != date.lengthOfMonth()) {
			weightedBalanceSum += transactions.get(0).getTransactionAfterBalance()
				* (date.lengthOfMonth() - previousDay + 1);
		}

		// 이자 계산
		return (long) (weightedBalanceSum * account.getInterestRate() / 12 / date.lengthOfMonth());
	}

	// 거래 후 잔액 조정 로직
	private Long adjustBalance(FinApiResponseDto.TransactionHistoryResponseDto.Transaction transaction) {
		Long balance = transaction.getTransactionAfterBalance();
		return transaction.getTransactionType().equals("1")
			? balance - transaction.getTransactionBalance()
			: balance + transaction.getTransactionBalance();
	}

}
