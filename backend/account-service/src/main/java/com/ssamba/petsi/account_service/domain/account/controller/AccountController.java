package com.ssamba.petsi.account_service.domain.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssamba.petsi.account_service.domain.account.dto.request.CheckAccountAuthRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.OpenAccountAuthRequestDto;
import com.ssamba.petsi.account_service.domain.account.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "AccountController", description = "계좌 컨트롤러")
public class AccountController {

	private final AccountService accountService;

	@GetMapping("/product")
	@Operation(summary = "전체 상품 조회하기")
	public ResponseEntity<?> getAllProducts() {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllProducts());
	}

	@PostMapping("/open-account-auth")
	@Operation(summary = "1원 송금")
	public ResponseEntity<?> openAccountAuth(@RequestHeader("X-User-Key") String userKey, @RequestBody OpenAccountAuthRequestDto openAccountAuthRequestDto) {
		accountService.openAccountAuth(openAccountAuthRequestDto, userKey);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@PostMapping("/check-account-auth")
	@Operation(summary = "1원 송금 인증")
	public ResponseEntity<?> checkAccountAuth(@RequestHeader("X-User-Key") String userKey, @RequestBody CheckAccountAuthRequestDto checkAccountAuthRequestDto) {
		accountService.checkAccountAuth(checkAccountAuthRequestDto, userKey);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}
