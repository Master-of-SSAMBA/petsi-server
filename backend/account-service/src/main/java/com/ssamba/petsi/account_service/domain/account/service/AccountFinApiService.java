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
import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiRequestDto;
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

	public void openAccountAuth(String userKey, String accountNo) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		FinApiRequestDto<FinApiRequestDto.OpenAccountAuthRequestDto> dto = FinApiRequestDto.OpenAccountAuthRequestDto
			.toOpenAccountAuth(userKey, accountNo);
		dto.getHeader().setApiKey(apiKey);

		HttpEntity<FinApiRequestDto<FinApiRequestDto.OpenAccountAuthRequestDto>> request = new HttpEntity<>(dto, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.openAccountAuth.getUrl(), request,
				String.class);
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND);
		}
	}

	public void checkAuthCode(String userKey, String accountNo, String authCode) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		FinApiRequestDto<FinApiRequestDto.CheckAuthCodeRequestDto> dto = FinApiRequestDto.CheckAuthCodeRequestDto
			.toCheckAuthCode(userKey, accountNo, authCode);
		dto.getHeader().setApiKey(apiKey);

		HttpEntity<FinApiRequestDto<FinApiRequestDto.CheckAuthCodeRequestDto>> request = new HttpEntity<>(dto, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.checkAuthCode.getUrl(), request,
				String.class);
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INVALID_CODE);
		}
	}

	public String createDemandDepositAccount(String accountTypeUniqueNo, String userKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		FinApiRequestDto<FinApiRequestDto.CreateAccountRequestDto> dto = FinApiRequestDto.CreateAccountRequestDto
			.toCreateAccount(userKey, accountTypeUniqueNo);
		dto.getHeader().setApiKey(apiKey);

		HttpEntity<FinApiRequestDto<FinApiRequestDto.CreateAccountRequestDto>> request = new HttpEntity<>(dto, headers);

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
			FinApiUrl.inquireDemandDepositAccountList.name(), userKey);
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
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		FinApiRequestDto<FinApiRequestDto.DeleteAccountRequestDto> dto = FinApiRequestDto.DeleteAccountRequestDto
			.toDeleteAccount(userKey, accountNo, refundAccountNo);
		dto.getHeader().setApiKey(apiKey);

		HttpEntity<FinApiRequestDto<FinApiRequestDto.DeleteAccountRequestDto>> request = new HttpEntity<>(dto, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.deleteDemandDepositAccount.getUrl(),
			request,
			String.class);

	}

	public FinApiResponseDto.AccountListResponseDto inquireDemandDepositAccount(String userKey, String accountNo) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		FinApiRequestDto<FinApiRequestDto.InquireAccount> dto = FinApiRequestDto.InquireAccount
			.toInquireAccount(userKey, accountNo);
		dto.getHeader().setApiKey(apiKey);

		HttpEntity<FinApiRequestDto<FinApiRequestDto.InquireAccount>> request = new HttpEntity<>(dto, headers);

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
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		FinApiRequestDto<FinApiRequestDto.TransactionHistory> dto = FinApiRequestDto.TransactionHistory
			.toHistoryList(userKey, account.getAccountNo(), startDate, endDate);
		dto.getHeader().setApiKey(apiKey);

		HttpEntity<FinApiRequestDto<FinApiRequestDto.TransactionHistory>> request = new HttpEntity<>(dto, headers);

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
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		FinApiRequestDto<FinApiRequestDto.UpdateTransfer> dto = FinApiRequestDto.UpdateTransfer
			.toUpdateTransfer(userKey, depositAccountNo, depositTransactionSummary, transactionBalance,
				withdrawalAccountNo, withdrawalTransactionSummary);
		dto.getHeader().setApiKey(apiKey);

		HttpEntity<FinApiRequestDto<FinApiRequestDto.UpdateTransfer>> request = new HttpEntity<>(dto, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(FinApiUrl.updateDemandDepositAccountTransfer.getUrl(), request,
				String.class);
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
		}
	}
}
