package com.ssamba.petsi.account_service.domain.account.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.AccountTransferRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.CheckAccountPassword;
import com.ssamba.petsi.account_service.domain.account.dto.request.CreateAccountRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.OpenAccountAuthRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.UpdateAccountNameRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.UpdateRecurringTransactionRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.AccountHolderNameResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAccountDetailsResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAccountHistoryResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAllAcountsResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAllProductsResponseDto;
import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.domain.account.entity.AccountProduct;
import com.ssamba.petsi.account_service.domain.account.entity.PetToAccount;
import com.ssamba.petsi.account_service.domain.account.entity.RecurringTransaction;
import com.ssamba.petsi.account_service.domain.account.enums.AccountStatus;
import com.ssamba.petsi.account_service.domain.account.repository.AccountProductRepository;
import com.ssamba.petsi.account_service.domain.account.repository.AccountRepository;
import com.ssamba.petsi.account_service.domain.account.repository.LinkedAccountRepository;
import com.ssamba.petsi.account_service.domain.account.repository.PetToAccountRepository;
import com.ssamba.petsi.account_service.domain.account.repository.RecurringTransactionRepository;
import com.ssamba.petsi.account_service.global.client.PetClient;
import com.ssamba.petsi.account_service.global.client.PictureClient;
import com.ssamba.petsi.account_service.global.dto.PetCustomDto;
import com.ssamba.petsi.account_service.global.dto.PictureMonthlyRequestDto;
import com.ssamba.petsi.account_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.account_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

	private final LinkedAccountRepository linkedAccountRepository;
	private final AccountProductRepository accountProductRepository;
	private final AccountRepository accountRepository;
	private final RecurringTransactionRepository recurringTransactionRepository;
	private final AccountFinApiService accountFinApiService;
	private final PetClient petClient;
	private final PictureClient pictureClient;
	private final PetToAccountRepository petToAccountRepository;

	@Transactional(readOnly = true)
	public List<GetAllProductsResponseDto> getAllProducts() {
		return accountProductRepository.findAll().stream()
			.map(GetAllProductsResponseDto::from)
			.toList();
	}

	public void openAccountAuth(OpenAccountAuthRequestDto openAccountAuthRequestDto, String userKey) {
		try {
			Long.parseLong(openAccountAuthRequestDto.getAccountNo());
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INVALID_ACCOUNT_NO);
		}
		accountFinApiService.openAccountAuth(userKey, openAccountAuthRequestDto.getAccountNo());
	}

	public void createAccountBySteps(CreateAccountRequestDto createAccountRequestDto, String userKey, Long userId) {

		//1. 인증 코드 체크
		accountFinApiService.checkAuthCode(userKey, createAccountRequestDto.getAccountNo(),
			createAccountRequestDto.getCode());

		//2. 계좌 상품 조회
		AccountProduct product = accountProductRepository.findById(createAccountRequestDto.getAccountProductId())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCOUNT_CATEGORY_NOT_FOUND));

		//3. 계좌 번호 갖고오기
		String accountNo = accountFinApiService.createDemandDepositAccount(product.getAccountTypeUniqueNo(), userKey);

		//4. DB에 저장
		Account account = accountRepository.save(
			CreateAccountRequestDto.toAccount(createAccountRequestDto, product, accountNo, userId, userKey));

		//5. 연결된 계좌 저장
		linkedAccountRepository.save(
			CreateAccountRequestDto.toLinkedAccount(createAccountRequestDto, account));

		//6. Pet 매핑
		List<Long> petIds = createAccountRequestDto.getPets();
		petIds.forEach(petId ->
			petToAccountRepository.save(
				new PetToAccount(null, account, petId)
			)
		);

		//7. 주기적 거래 저장
		if (createAccountRequestDto.getIsAuto()) {
			recurringTransactionRepository.save(CreateAccountRequestDto.toRecurringTransaction(
				createAccountRequestDto, account));
		}

	}

	@Transactional(readOnly = true)
	public GetAllAcountsResponseDto getAllAccounts(Long userId, String userKey) {
		int pictureCnt = pictureClient.getMonthlyPicture(
			new PictureMonthlyRequestDto(userId, 2024, LocalDate.now().getMonth().getValue())).size();
		double interest = Math.min(1 + pictureCnt * 0.1, 3);

		List<PetCustomDto> petList = petClient.findAllWithPetCustomDto(userId);

		List<FinApiResponseDto.AccountListResponseDto> accountList = accountFinApiService.inquireDemandDepositAccountList(userKey);
		List<Account> localAccountList = accountRepository.findAllByUserIdAndStatus(userId, AccountStatus.ACTIVATED.getValue());

		List<GetAllAcountsResponseDto.AccountDto> returnList = localAccountList.stream()
			.map(account -> {
				GetAllAcountsResponseDto.AccountDto dto = GetAllAcountsResponseDto.AccountDto.from(account);

				// PetToAccount에서 첫 번째 petId 가져오기
				List<Long> petIds = account.getPetToAccounts().stream()
					.map(PetToAccount::getPetId)
					.toList();

				// petList가 비어 있거나, petId와 일치하는 PetCustomDto를 찾지 못하면 null 설정
				dto.setPetPicture(Optional.ofNullable(petIds)
					.filter(ids -> !ids.isEmpty())
					.flatMap(ids -> petList.stream()
						.filter(p -> p.getPetId().equals(ids.get(0)))
						.findFirst()
						.map(PetCustomDto::getPetImg))
					.orElse(null));


				// FinApiResponseDto와 accountNo를 매칭하여 balance 설정
				return accountList.stream()
					.filter(dtoAccount -> dtoAccount.getAccountNo().equals(dto.getAccountNo()))
					.findFirst()
					.map(dtoAccount -> {
						dto.setBalance(dtoAccount.getAccountBalance());
						return dto;
					})
					.orElseThrow(() -> new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR));
			})
			.toList();


		return new GetAllAcountsResponseDto(pictureCnt, interest, returnList);
	}

	public void deleteAccount(Long userId, String userKey, Long accountId) {
		Account account = accountRepository.findByAccountIdAndStatusAndUserId(accountId,
			AccountStatus.ACTIVATED.getValue(), userId).orElseThrow(
				() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND)
		);

		account.setStatus(AccountStatus.INACTIVATED.getValue());
		if (account.getRecurringTransaction() != null) {
			recurringTransactionRepository.delete(account.getRecurringTransaction());
		}

		accountFinApiService.deleteDemandDepositAccount(userKey, account.getAccountNo(),
			account.getLinkedAccount().getAccountNumber());
	}

	public void updateRecurringTransaction(UpdateRecurringTransactionRequestDto updateRecurringTransactionRequestDto,
		Long userId) {
		Account account = accountRepository.findByAccountIdAndStatusAndUserId(
			updateRecurringTransactionRequestDto.getAccountId(), AccountStatus.ACTIVATED.getValue(), userId).orElseThrow(
				() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND)
		);

		if (account.getRecurringTransaction().getAmount() == updateRecurringTransactionRequestDto.getAmount()
			&& account.getRecurringTransaction().getPaymentDate() == updateRecurringTransactionRequestDto.getDay()) {
			throw new BusinessLogicException(ExceptionCode.EMPTY_REQUEST);
		}

		account.getRecurringTransaction().setAmount(updateRecurringTransactionRequestDto.getAmount());
		account.getRecurringTransaction().setPaymentDate(updateRecurringTransactionRequestDto.getDay());

		LocalDate nextTransactionDate = account.getRecurringTransaction().getNextTransactionDate();
		int day = account.getRecurringTransaction().getPaymentDate();

		int year = nextTransactionDate.getYear();
		int month = nextTransactionDate.getMonthValue();

		YearMonth yearMonth = YearMonth.of(year, month);
		int lastDayOfMonth = yearMonth.lengthOfMonth();

		int validDay = Math.min(day, lastDayOfMonth);

		LocalDate updatedDate = LocalDate.of(year, month, validDay);
		account.getRecurringTransaction().setNextTransactionDate(updatedDate);

	}

	public void updateAccountName(UpdateAccountNameRequestDto updateAccountNameRequestDto, Long userId) {
		Account account = accountRepository.findByAccountIdAndStatusAndUserId(updateAccountNameRequestDto.getAccountId()
		, AccountStatus.ACTIVATED.getValue(), userId).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND)
		);
		account.setName(updateAccountNameRequestDto.getName());
	}

	@Transactional(readOnly = true)
	public GetAccountDetailsResponseDto getAccountDetails(Long userId, String userKey, Long accountId) {
		Account account = accountRepository.findByIdWithRecurringTransaction(accountId).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND)
		);

		GetAccountDetailsResponseDto returnDto = new GetAccountDetailsResponseDto(account);
		LocalDate nowDate = LocalDate.now();
		LocalDate getCreateDate = account.getCreatedAt().toLocalDate();
		LocalDate getExpirationDate = account.getMaturityDate();
		int createDdays = (int)ChronoUnit.DAYS.between(getCreateDate, nowDate);
		int expireDdays = (int)ChronoUnit.DAYS.between(nowDate, getExpirationDate);
		returnDto.setCreateDdays(createDdays);
		returnDto.setExpireDdays(expireDdays);

		returnDto.setBalance(accountFinApiService.inquireDemandDepositAccount(userKey, account.getAccountNo()).getAccountBalance());

		returnDto.setTransactionHistory(getAccountHistory(userId, userKey, accountId, 0, 7));
		return returnDto;
	}

	public List<GetAccountHistoryResponseDto> getAccountHistory(Long userId, String userKey, Long accountId, int page,
		int sortOption) {
		Pageable pageable = PageRequest.of(page, 12);

		Account account = accountRepository.findByAccountIdAndStatusAndUserId(accountId,
			AccountStatus.ACTIVATED.getValue(), userId).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND)
		);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate nowDate = LocalDate.now();
		String endDate = nowDate.format(formatter);
		String startDate = "20200101";
		if (sortOption != 0) {
			LocalDate monthSetting = nowDate;
			if(sortOption != 1) {
				monthSetting = nowDate.minusDays(sortOption);
			}
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

	public void accountTransfer(Long userId, String userKey, AccountTransferRequestDto accountTransferRequestDto) {
		Account account = accountRepository.findByAccountIdAndStatusAndUserId(accountTransferRequestDto.getAccountId(),
			AccountStatus.ACTIVATED.getValue(), userId).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND));

		String depositAccountNo = accountTransferRequestDto.getDestinationAccountNo();
		String depositTransactionSummary = accountTransferRequestDto.getDestinationDescription();
		Long transactionBalance = accountTransferRequestDto.getAmount();
		String withdrawalAccountNo = account.getAccountNo();
		String withdrawalTransactionSummary = accountTransferRequestDto.getDescription();
		accountFinApiService.updateDemandDepositAccountTransfer(userKey,
			depositAccountNo, depositTransactionSummary, transactionBalance,
			withdrawalAccountNo, withdrawalTransactionSummary);
	}

	public AccountHolderNameResponseDto getAccountHolderName(String userKey, String accountNo) {
		FinApiResponseDto.InquireDemandDepositAccountHolderName dto = accountFinApiService
			.InquireDemandDepositAccountHolderName(accountNo, userKey);
		return new AccountHolderNameResponseDto(dto);

	}

	public void checkAccountPassword(Long userId, CheckAccountPassword checkAccountPassword) {
		Account account = accountRepository.findByAccountIdAndStatusAndUserIdAndAccountNo(checkAccountPassword.getAccountId(),
			AccountStatus.ACTIVATED.getValue(), userId, checkAccountPassword.getAccountNo()).orElseThrow(
				() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND));
		
		System.out.println(checkAccountPassword.getPassword() + " " + account.getAccountNo() + " " + account.getPassword());
		if(account.getPassword().equals(checkAccountPassword.getPassword())) {
			throw new BusinessLogicException(ExceptionCode.INVALID_ACCOUNT_PASSWORD);
		}
	}
}
