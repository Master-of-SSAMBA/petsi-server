package com.ssamba.petsi.account_service.domain.account.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.CheckAccountAuthDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.CreateAccountRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.OpenAccountAuthRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.UpdateAccountNameRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.UpdateRecurringTransactionRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAccountDetailsResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAccountHistoryResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAllAcountsResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAllProductsResponseDto;
import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.domain.account.entity.AccountProduct;
import com.ssamba.petsi.account_service.domain.account.entity.RecurringTransaction;
import com.ssamba.petsi.account_service.domain.account.enums.AccountStatus;
import com.ssamba.petsi.account_service.domain.account.repository.AccountProductRepository;
import com.ssamba.petsi.account_service.domain.account.repository.AccountRepository;
import com.ssamba.petsi.account_service.domain.account.repository.LinkedAccountRepository;
import com.ssamba.petsi.account_service.domain.account.repository.RecurringTransactionRepository;
import com.ssamba.petsi.account_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.account_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final LinkedAccountRepository linkedAccountRepository;
	private final AccountProductRepository accountProductRepository;
	private final AccountRepository accountRepository;
	private final RecurringTransactionRepository recurringTransactionRepository;
	private final AccountFinApiService accountFinApiService;

	@Transactional(readOnly = true)
	public List<GetAllProductsResponseDto> getAllProducts() {
		return accountProductRepository.findAll().stream()
			.map(GetAllProductsResponseDto::from)
			.collect(Collectors.toList());
	}

	public void openAccountAuth(OpenAccountAuthRequestDto openAccountAuthRequestDto, String userKey) {
		accountFinApiService.openAccountAuth(userKey, openAccountAuthRequestDto.getAccountNo());
	}

	@Transactional
	public void createAccountBySteps(CreateAccountRequestDto createAccountRequestDto, String userKey, Long userId) {

		CheckAccountAuthDto checkAccountAuthDto = new CheckAccountAuthDto(createAccountRequestDto);
		checkAccountAuthDto.setUserKey(userKey);

		accountFinApiService.checkAuthCode(userKey, createAccountRequestDto.getAccountNo(),
			createAccountRequestDto.getCode());

		AccountProduct product = accountProductRepository.findById(createAccountRequestDto.getAccountProductId())
			.orElseThrow();

		String accountNo = accountFinApiService.createDemandDepositAccount(product.getAccountTypeUniqueNo(), userKey);

		Account account = CreateAccountRequestDto.toAccount(createAccountRequestDto, product, accountNo, userId);
		account = accountRepository.save(account);

		linkedAccountRepository.save(
			CreateAccountRequestDto.toLinkedAccount(createAccountRequestDto, account));

		if (createAccountRequestDto.getIsAuto()) {
			RecurringTransaction recurringTransaction = CreateAccountRequestDto.toRecurringTransaction(
				createAccountRequestDto, account);
			recurringTransactionRepository.save(recurringTransaction);
		}

	}

	@Transactional(readOnly = true)
	public List<?> getAllAccounts(Long userId, String userKey) {
		//todo: 월별 사진 인증 횟수 및 이자율 계산해서 같이 return
		//todo: 계좌 내 매핑된 pet의 사진 같이 return

		List<FinApiResponseDto.AccountListResponseDto> accountList = accountFinApiService.inquireDemandDepositAccountList(userKey);
		List<Account> localAccountList = accountRepository.findAllByUserIdAndStatus(userId, AccountStatus.ACTIVATED.getValue());

		List<GetAllAcountsResponseDto> returnList = new ArrayList<>();
		addAccounts: for(Account account : localAccountList) {
			GetAllAcountsResponseDto dto = GetAllAcountsResponseDto.from(account);
			for(FinApiResponseDto.AccountListResponseDto dtoAccount : accountList) {
				if(dtoAccount.getAccountNo().equals(dto.getAccountNo())) {
					dto.setBalance(dtoAccount.getAccountBalance());
					returnList.add(dto);
					continue addAccounts;
				}
			}
		}

		return returnList;
	}

	@Transactional
	public void deleteAccount(Long userId, String userKey, Long accountId) {
		Account account = accountRepository.findByIdWithRecurringTransaction(accountId);
		if (userId != account.getUserId()) {
			//todo: 계좌주와 로그인 유저 불일치 에러
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}

		if (!account.getStatus().equals(AccountStatus.ACTIVATED.getValue())) {
			//todo: 계좌 활성 상태가 아님
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}

		account.setStatus(AccountStatus.INACTIVATED.getValue());
		if (account.getRecurringTransaction() != null) {
			recurringTransactionRepository.delete(account.getRecurringTransaction());
		}

		accountFinApiService.deleteDemandDepositAccount(userKey, account.getAccountNo(),
			account.getLinkedAccount().getAccountNumber());
	}

	@Transactional
	public void updateRecurringTransaction(UpdateRecurringTransactionRequestDto updateRecurringTransactionRequestDto,
		Long userId) {
		Account account = accountRepository.findByIdWithRecurringTransaction(
			updateRecurringTransactionRequestDto.getAccountId());

		if (userId != account.getUserId()) {
			//계좌주 로그인 유저 불일치 에러
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}

		if (!account.getStatus().equals(AccountStatus.ACTIVATED.getValue())) {
			//활성 상태가 아닌 계좌 에러
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}

		if (account.getRecurringTransaction().getAmount() == updateRecurringTransactionRequestDto.getAmount()
			&& account.getRecurringTransaction().getFrequency() == updateRecurringTransactionRequestDto.getDay()) {
			//변동 사항 없음
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}

		account.getRecurringTransaction().setAmount(updateRecurringTransactionRequestDto.getAmount());
		account.getRecurringTransaction().setFrequency(updateRecurringTransactionRequestDto.getDay());

		LocalDate nextTransactionDate = account.getRecurringTransaction().getNextTransactionDate();
		int day = account.getRecurringTransaction().getFrequency();

		int year = nextTransactionDate.getYear();
		int month = nextTransactionDate.getMonthValue();

		YearMonth yearMonth = YearMonth.of(year, month);
		int lastDayOfMonth = yearMonth.lengthOfMonth();

		int validDay = Math.min(day, lastDayOfMonth);

		LocalDate updatedDate = LocalDate.of(year, month, validDay);
		account.getRecurringTransaction().setNextTransactionDate(updatedDate);

	}

	@Transactional
	public void updateAccountName(UpdateAccountNameRequestDto updateAccountNameRequestDto, Long userId) {
		Account account = accountRepository.findById(updateAccountNameRequestDto.getAccountId()).orElseThrow();
		if (!account.getStatus().equals(AccountStatus.ACTIVATED.getValue())) {
			//활성화 상태가 아닐 때 에러
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
		if (account.getUserId() != userId) {
			//계좌주와 로그인 유저 불일치 에러
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
		account.setName(updateAccountNameRequestDto.getName());
	}

	@Transactional(readOnly = true)
	public GetAccountDetailsResponseDto getAccountDetails(Long userId, String userKey, Long accountId) {
		Account account = accountRepository.findByIdWithRecurringTransaction(accountId);
		if (!account.getStatus().equals(AccountStatus.ACTIVATED.getValue())) {
			//활성화 상태가 아닐 때 에러
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
		if (account.getUserId() != userId) {
			//계좌주와 로그인 유저 불일치 에러
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}

		//한달 내역 가져오기
		GetAccountDetailsResponseDto returnDto = new GetAccountDetailsResponseDto(account);
		LocalDate nowDate = LocalDate.now();
		LocalDate getCreateDate = account.getCreatedAt().toLocalDate();
		LocalDate getExpirationDate = account.getMaturityDate();
		int createDdays = (int)ChronoUnit.DAYS.between(getCreateDate, nowDate);
		int expireDdays = (int)ChronoUnit.DAYS.between(nowDate, getExpirationDate);
		returnDto.setCreateDdays(createDdays);
		returnDto.setExpireDdays(expireDdays);

		//잔액 업데이트
		returnDto.setBalance(accountFinApiService.inquireDemandDepositAccount(userKey, account.getAccountNo()).getAccountBalance());

		returnDto.setTransactionHistory(getAccountHistory(userId, userKey, accountId, 0, 1));
		return returnDto;
	}

	public List<GetAccountHistoryResponseDto> getAccountHistory(Long userId, String userKey, Long accountId, int page,
		Integer sortOption) {
		Pageable pageable = PageRequest.of(page, 12);

		Account account = accountRepository.findByIdWithRecurringTransaction(accountId);
		if (!account.getStatus().equals(AccountStatus.ACTIVATED.getValue())) {
			//활성화 상태가 아닐 때 에러
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
		if (account.getUserId() != userId) {
			//계좌주와 로그인 유저 불일치 에러
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate nowDate = LocalDate.now();
		String endDate = nowDate.format(formatter);
		String startDate = "20200101";
		if (sortOption != null) {
			LocalDate monthSetting = nowDate.minusMonths(sortOption);
			startDate = monthSetting.format(formatter);
		}

		FinApiResponseDto.TransactionHistoryResponseDto returndto = accountFinApiService.inquireTransactionHistoryList(
			account, startDate, endDate, userKey);
		List<FinApiResponseDto.TransactionHistoryResponseDto.Transaction> list = returndto.getList()
			.subList(page * 12, Math.min(page * 12 + 12, returndto.getList().size()));

		return list.stream()
			.map(GetAccountHistoryResponseDto::from)
			.collect(Collectors.toList());
	}
}
