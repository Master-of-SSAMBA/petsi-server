package com.ssamba.petsi.account_service.domain.account.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiHeaderRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiResponseDto;
import com.ssamba.petsi.account_service.domain.account.entity.Account;
import com.ssamba.petsi.account_service.domain.account.enums.FinApiUrl;
import com.ssamba.petsi.account_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.account_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AccountFinApiService {

	private final RestTemplate restTemplate;

	@Value("${spring.fin.api-key}")
	private String apiKey;

	@Value("${spring.fin.manager-account}")
	private String managerAccount;

	public void openAccountAuth(String userKey, String accountNo) {
		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.openAccountAuth.name(),
			FinApiUrl.openAccountAuth.name());
		header.setUserKey(userKey);
		header.setApiKey(apiKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("accountNo", accountNo);
		finApiDto.put("authText", "petsi");

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.openAccountAuth.getUrl(), request,
				String.class);
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND);
		}
	}

	public void checkAuthCode(String userKey, String accountNo, String authCode) {
		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.checkAuthCode.name(),
			FinApiUrl.checkAuthCode.name());
		header.setUserKey(userKey);
		header.setApiKey(apiKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("accountNo", accountNo);
		finApiDto.put("authText", "petsi");
		finApiDto.put("authCode", authCode);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.checkAuthCode.getUrl(), request,
				String.class);
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INVALID_CODE);
		}
	}

	public String createDemandDepositAccount(String accountTypeUniqueNo, String userKey) {
		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.createDemandDepositAccount.name(),
			FinApiUrl.createDemandDepositAccount.name());
		header.setUserKey(userKey);
		header.setApiKey(apiKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("accountTypeUniqueNo", accountTypeUniqueNo);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		ParameterizedTypeReference<FinApiResponseDto<FinApiResponseDto.CreateAccountResponseDto>> responseType =
			new ParameterizedTypeReference<>() {
			};

		try {
			ResponseEntity<FinApiResponseDto<FinApiResponseDto.CreateAccountResponseDto>> response = restTemplate.exchange(
				FinApiUrl.createDemandDepositAccount.getUrl(),
				HttpMethod.POST,
				request,
				responseType
			);

			return response.getBody().getRec().getAccountNo();
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
	}

	public List<FinApiResponseDto.AccountListResponseDto> inquireDemandDepositAccountList(String userKey) {
		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.inquireDemandDepositAccountList.name(),
			FinApiUrl.inquireDemandDepositAccountList.name());
		header.setUserKey(userKey);
		header.setApiKey(apiKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		ParameterizedTypeReference<FinApiResponseDto<List<FinApiResponseDto.AccountListResponseDto>>> responseType =
			new ParameterizedTypeReference<>() {
			};

		ResponseEntity<FinApiResponseDto<List<FinApiResponseDto.AccountListResponseDto>>> response =
			restTemplate.exchange(
				FinApiUrl.inquireDemandDepositAccountList.getUrl(),
				HttpMethod.POST,
				request,
				responseType
			);

		return response.getBody().getRec();
	}

	public void deleteDemandDepositAccount(String userKey, String accountNo, String refundAccountNo) {
		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.deleteDemandDepositAccount.name(),
			FinApiUrl.deleteDemandDepositAccount.name());
		header.setUserKey(userKey);
		header.setApiKey(apiKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("accountNo", accountNo);
		finApiDto.put("refundAccountNo", refundAccountNo);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.deleteDemandDepositAccount.getUrl(),
			request,
			String.class);

	}

	public FinApiResponseDto.AccountListResponseDto inquireDemandDepositAccount(String userKey, String accountNo) {
		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.inquireDemandDepositAccount.name(),
			FinApiUrl.inquireDemandDepositAccount.name());
		header.setUserKey(userKey);
		header.setApiKey(apiKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("accountNo", accountNo);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		ParameterizedTypeReference<FinApiResponseDto<FinApiResponseDto.AccountListResponseDto>> responseType =
			new ParameterizedTypeReference<>() {
			};

		ResponseEntity<FinApiResponseDto<FinApiResponseDto.AccountListResponseDto>> response =
			restTemplate.exchange(
				FinApiUrl.inquireDemandDepositAccount.getUrl(),
				HttpMethod.POST,
				request,
				responseType
			);

		return response.getBody().getRec();
	}

	public FinApiResponseDto.TransactionHistoryResponseDto inquireTransactionHistoryList(
		Account account, String startDate, String endDate, String userKey) {
		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.inquireTransactionHistoryList.name(),
			FinApiUrl.inquireTransactionHistoryList.name());
		header.setUserKey(userKey);
		header.setApiKey(apiKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("accountNo", account.getAccountNo());
		finApiDto.put("startDate", startDate);
		finApiDto.put("endDate", endDate);
		finApiDto.put("transactionType", "A");
		finApiDto.put("orderByType", "DESC");

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		ParameterizedTypeReference<FinApiResponseDto<FinApiResponseDto.TransactionHistoryResponseDto>> responseType =
			new ParameterizedTypeReference<>() {
			};

		ResponseEntity<FinApiResponseDto<FinApiResponseDto.TransactionHistoryResponseDto>> response =
			restTemplate.exchange(
				FinApiUrl.inquireTransactionHistoryList.getUrl(),
				HttpMethod.POST,
				request,
				responseType
			);

		return response.getBody().getRec();
	}

	public void updateDemandDepositAccountTransfer(String userKey, String depositAccountNo, String depositTransactionSummary, Long transactionBalance, String withdrawalAccountNo, String withdrawalTransactionSummary) {
		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.updateDemandDepositAccountTransfer.name(),
			FinApiUrl.updateDemandDepositAccountTransfer.name());
		header.setUserKey(userKey);
		header.setApiKey(apiKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("depositAccountNo", depositAccountNo);
		finApiDto.put("depositTransactionSummary", depositTransactionSummary);
		finApiDto.put("transactionBalance", transactionBalance);
		finApiDto.put("withdrawalAccountNo", withdrawalAccountNo);
		finApiDto.put("withdrawalTransactionSummary", withdrawalTransactionSummary);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.updateDemandDepositAccountTransfer.getUrl(), request,
				String.class);
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
	}


	public FinApiResponseDto.InquireDemandDepositAccountHolderName InquireDemandDepositAccountHolderName(
		String accountNo, String userKey) {
		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.inquireDemandDepositAccountHolderName.name(),
			FinApiUrl.inquireDemandDepositAccountHolderName.name());
		header.setUserKey(userKey);
		header.setApiKey(apiKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("accountNo", accountNo);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		ParameterizedTypeReference<FinApiResponseDto<FinApiResponseDto.InquireDemandDepositAccountHolderName>> responseType =
			new ParameterizedTypeReference<>() {
			};

		ResponseEntity<FinApiResponseDto<FinApiResponseDto.InquireDemandDepositAccountHolderName>> response =
			restTemplate.exchange(
				FinApiUrl.inquireDemandDepositAccountHolderName.getUrl(),
				HttpMethod.POST,
				request,
				responseType
			);

		return response.getBody().getRec();
	}

	public void addInterest(Account account, Long calculatedInterest) {
		FinApiHeaderRequestDto header = new FinApiHeaderRequestDto(FinApiUrl.updateDemandDepositAccountTransfer.name(),
			FinApiUrl.updateDemandDepositAccountTransfer.name());
		header.setUserKey(account.getUserKey());
		header.setApiKey(apiKey);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> finApiDto = new HashMap<>();
		finApiDto.put("Header", header);
		finApiDto.put("depositAccountNo", account.getAccountNo());
		finApiDto.put("depositTransactionSummary", "정기 적금 이자");
		finApiDto.put("transactionBalance", calculatedInterest);
		finApiDto.put("withdrawalAccountNo", managerAccount);
		finApiDto.put("withdrawalTransactionSummary", "이자지급");

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(finApiDto, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.updateDemandDepositAccountTransfer.getUrl(), request,
				String.class);
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
	}
}
