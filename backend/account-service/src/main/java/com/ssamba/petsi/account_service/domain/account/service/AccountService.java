package com.ssamba.petsi.account_service.domain.account.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiHeaderRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.CheckAccountAuthDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.CreateAccountRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.OpenAccountAuthRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAllProductsResponseDto;
import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.domain.account.entity.AccountProduct;
import com.ssamba.petsi.account_service.domain.account.entity.LinkedAccount;
import com.ssamba.petsi.account_service.domain.account.entity.RecurringTransaction;
import com.ssamba.petsi.account_service.domain.account.enums.FinApiUrl;
import com.ssamba.petsi.account_service.domain.account.repository.AccountProductRepository;
import com.ssamba.petsi.account_service.domain.account.repository.AccountRepository;
import com.ssamba.petsi.account_service.domain.account.repository.LinkedAccountRepository;
import com.ssamba.petsi.account_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.account_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final LinkedAccountRepository linkedAccountRepository;
	private final AccountProductRepository accountProductRepository;
	private final AccountRepository accountRepository;
	private final com.ssamba.petsi.account_service.domain.account.repository.recurringTransactionRepository recurringTransactionRepository;

	public List<GetAllProductsResponseDto> getAllProducts() {
		return accountProductRepository.findAll().stream()
			.map(GetAllProductsResponseDto::from)
			.collect(Collectors.toList());
	}

	public void openAccountAuth(OpenAccountAuthRequestDto openAccountAuthRequestDto, String userKey) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			//todo: accountNo로  bankName 일치하는지 확인 (유효한 계좌)

			FinApiHeaderRequestDto Header = new FinApiHeaderRequestDto(FinApiUrl.openAccountAuth.name(),
				FinApiUrl.openAccountAuth.name());
			Header.setUserKey(userKey);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Map<String, Object> finApiDto = new HashMap<>();
			finApiDto.put("Header", Header);
			finApiDto.put("accountNo", openAccountAuthRequestDto.getAccountNo());
			finApiDto.put("authText", "petsi");

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

			ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.openAccountAuth.getUrl(), request,
				String.class);
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}

	}

	@Transactional
	public void createAccountBySteps(CreateAccountRequestDto createAccountRequestDto, String userKey, Long userId) {

		CheckAccountAuthDto checkAccountAuthDto = new CheckAccountAuthDto(createAccountRequestDto);
		checkAccountAuthDto.setUserKey(userKey);

		//1원 송금 검증
		checkAccountAuth(checkAccountAuthDto);

		AccountProduct product = accountProductRepository.findById(createAccountRequestDto.getAccountProductId()).orElseThrow();

		//todo 1 finAPI 계좌 생성
		String accountNo = makeAccount(product.getAccountTypeUniqueNo(), userKey);

		//todo 2 account entity 저장
		Account account = CreateAccountRequestDto.toAccount(createAccountRequestDto, product, accountNo, userId);
		account = accountRepository.save(account);

		LinkedAccount linkedAccount = linkedAccountRepository.save(CreateAccountRequestDto.toLinkedAccount(createAccountRequestDto, account));

		//todo 3 isAuto 일때 주기적 거래 생성 후 저장
		if(createAccountRequestDto.getIsAuto()) {
			RecurringTransaction recurringTransaction = CreateAccountRequestDto.toRecurringTransaction(createAccountRequestDto, account);
			recurringTransactionRepository.save(recurringTransaction);
		}

	}

	public void checkAccountAuth(CheckAccountAuthDto checkAccountAuthDto) throws BusinessLogicException {
		RestTemplate restTemplate = new RestTemplate();
		//todo: accountNo로  bankName 일치하는지 확인 (유효한 계좌)

		FinApiHeaderRequestDto Header = new FinApiHeaderRequestDto(FinApiUrl.checkAuthCode.name(),
			FinApiUrl.checkAuthCode.name());
		Header.setUserKey(checkAccountAuthDto.getUserKey());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", Header);
		finApiDto.put("accountNo", checkAccountAuthDto.getAccountNo());
		finApiDto.put("authText", "petsi");
		finApiDto.put("authCode", checkAccountAuthDto.getCode());

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		ResponseEntity<String> response =  restTemplate.postForEntity(FinApiUrl.checkAuthCode.getUrl(), request,
				String.class);

	}

	public String makeAccount(String accountTypeUniqueNo, String userKey) {
		RestTemplate restTemplate = new RestTemplate();

		FinApiHeaderRequestDto Header = new FinApiHeaderRequestDto(FinApiUrl.createDemandDepositAccount.name(),
			FinApiUrl.createDemandDepositAccount.name());
		Header.setUserKey(userKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", Header);
		finApiDto.put("accountTypeUniqueNo", accountTypeUniqueNo);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		ParameterizedTypeReference<FinApiResponseDto<FinApiResponseDto.CreateAccountResponseDto>> responseType =
			new ParameterizedTypeReference<>() {};

		ResponseEntity<FinApiResponseDto<FinApiResponseDto.CreateAccountResponseDto>> response = restTemplate.exchange(
			FinApiUrl.checkAuthCode.getUrl(),
			HttpMethod.POST,
			request,
			responseType
		);

		return response.getBody().getREC().getAccountNo();
	}
}
