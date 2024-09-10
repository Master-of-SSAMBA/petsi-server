package com.ssamba.petsi.account_service.domain.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssamba.petsi.account_service.domain.account.dto.response.GetAllProductsResponseDto;
import com.ssamba.petsi.account_service.domain.account.repository.AccountProductRepository;
import com.ssamba.petsi.account_service.domain.account.repository.AccountRepository;

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

}
