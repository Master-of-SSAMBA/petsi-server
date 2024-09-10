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
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAllAcountsResponseDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAllProductsResponseDto;
import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.domain.account.entity.AccountProduct;
import com.ssamba.petsi.account_service.domain.account.entity.RecurringTransaction;
import com.ssamba.petsi.account_service.domain.account.enums.FinApiUrl;
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

	public List<GetAllProductsResponseDto> getAllProducts() {
		return accountProductRepository.findAll().stream()
			.map(GetAllProductsResponseDto::from)
			.collect(Collectors.toList());
	}

	public void openAccountAuth(OpenAccountAuthRequestDto openAccountAuthRequestDto, String userKey) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			//todo: accountNo로  bankName 일치하는지 확인 (유효한 계좌)

			FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.openAccountAuth.name(),
				FinApiUrl.openAccountAuth.name());
			header.setUserKey(userKey);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Map<String, Object> finApiDto = new HashMap<>();
			finApiDto.put("Header", header);
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

		checkAccountAuth(checkAccountAuthDto);

		AccountProduct product = accountProductRepository.findById(createAccountRequestDto.getAccountProductId())
			.orElseThrow();

		String accountNo = makeAccount(product.getAccountTypeUniqueNo(), userKey);

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

	public void checkAccountAuth(CheckAccountAuthDto checkAccountAuthDto) throws BusinessLogicException {
		RestTemplate restTemplate = new RestTemplate();

		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.checkAuthCode.name(),
			FinApiUrl.checkAuthCode.name());
		header.setUserKey(checkAccountAuthDto.getUserKey());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("accountNo", checkAccountAuthDto.getAccountNo());
		finApiDto.put("authText", "petsi");
		finApiDto.put("authCode", checkAccountAuthDto.getCode());

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.checkAuthCode.getUrl(), request,
			String.class);

	}

	public String makeAccount(String accountTypeUniqueNo, String userKey) {
		RestTemplate restTemplate = new RestTemplate();

		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.createDemandDepositAccount.name(),
			FinApiUrl.createDemandDepositAccount.name());
		header.setUserKey(userKey);

		System.out.println(header.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("accountTypeUniqueNo", accountTypeUniqueNo);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		ParameterizedTypeReference<FinApiResponseDto<FinApiResponseDto.CreateAccountResponseDto>> responseType =
			new ParameterizedTypeReference<>() {

			};

		ResponseEntity<FinApiResponseDto<FinApiResponseDto.CreateAccountResponseDto>> response = restTemplate.exchange(
			FinApiUrl.createDemandDepositAccount.getUrl(),
			HttpMethod.POST,
			request,
			responseType
		);

		System.out.println(response.toString());
		System.out.println(response.getBody());
		System.out.println(response.getBody().toString());

		return response.getBody().getRec().getAccountNo();
	}

	public List<?> getAllAccounts(Long userId) {
		//todo: 월별 사진 인증 횟수 및 이자율 계산해서 같이 return
		//todo: 계좌 내 매핑된 pet의 사진 같이 return
		return accountRepository.findAllByUserId(userId).stream()
			.map(GetAllAcountsResponseDto::from)
			.collect(Collectors.toList());
	}
}
