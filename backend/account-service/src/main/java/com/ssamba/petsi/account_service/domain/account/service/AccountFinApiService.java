package com.ssamba.petsi.account_service.domain.account.service;

import java.util.HashMap;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AccountFinApiService {

	private final RestTemplate restTemplate;

	@Value("${spring.fin.api-key}")
	private String apiKey;

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

		ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.openAccountAuth.getUrl(), request,
			String.class);
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

		ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.checkAuthCode.getUrl(), request,
			String.class);
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

		ResponseEntity<FinApiResponseDto<FinApiResponseDto.CreateAccountResponseDto>> response = restTemplate.exchange(
			FinApiUrl.createDemandDepositAccount.getUrl(),
			HttpMethod.POST,
			request,
			responseType
		);
		return response.getBody().getRec().getAccountNo();
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
		finApiDto.put("orderByType", "desc");

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
}
