package com.ssamba.petsi.account_service.domain.account.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssamba.petsi.account_service.domain.account.dto.fin.FinApiHeaderDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.OpenAccountAuthRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.response.GetAllProductsResponseDto;
import com.ssamba.petsi.account_service.domain.account.enums.FinApiUrl;
import com.ssamba.petsi.account_service.domain.account.repository.AccountProductRepository;
import com.ssamba.petsi.account_service.domain.account.repository.AccountRepository;
import com.ssamba.petsi.account_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.account_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;
	private final AccountProductRepository accountProductRepository;

	public List<GetAllProductsResponseDto> getAllProducts() {
		return accountProductRepository.findAll().stream()
			.map(GetAllProductsResponseDto::from)
			.collect(Collectors.toList());
	}

	public void openAccountAuth(OpenAccountAuthRequestDto openAccountAuthRequestDto, String userKey) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			//todo: accountNo로  bankName 일치하는지 확인 (유효한 계좌)

			FinApiHeaderDto Header = new FinApiHeaderDto(FinApiUrl.openAccountAuth.name(),
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

}
