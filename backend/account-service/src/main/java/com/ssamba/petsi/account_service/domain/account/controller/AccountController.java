package com.ssamba.petsi.account_service.domain.account.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssamba.petsi.account_service.domain.account.dto.request.CreateAccountRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.OpenAccountAuthRequestDto;
import com.ssamba.petsi.account_service.domain.account.dto.request.UpdateAccountNameRequestDto;
import com.ssamba.petsi.account_service.domain.account.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
	public ResponseEntity<?> openAccountAuth(@RequestHeader("X-User-Key") String userKey,
		@RequestBody OpenAccountAuthRequestDto openAccountAuthRequestDto) {
		accountService.openAccountAuth(openAccountAuthRequestDto, userKey);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@PostMapping("/account")
	@Operation(summary = "1원 송금 인증 및 계좌 생성")
	public ResponseEntity<?> checkAccountAuth(@RequestHeader("X-User-Id") Long userId,
		@RequestHeader("X-User-Key") String userKey, @RequestBody CreateAccountRequestDto createAccountRequestDto) {
		accountService.createAccountBySteps(createAccountRequestDto, userKey, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}

	@GetMapping("/account")
	@Operation(summary = "계좌 전체 조회")
	public ResponseEntity<?> getAllAccounts(@RequestHeader("X-User-Id") Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts(userId));
	}

	@DeleteMapping("/account")
	@Operation(summary = "계좌 해지")
	public ResponseEntity<?> deleteAccount(@RequestHeader("X-User-Id") Long userId,
		@RequestHeader("X-User-Key") String userKey, @RequestBody Map<String, Long> request) {
		accountService.deleteAccount(userId, userKey, request.get("accountId"));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@PatchMapping("/account/name")
	@Operation(summary = "사용자 계좌명 변경")
	public ResponseEntity<?> updateAccountName(@RequestHeader("X-User-Id") Long userId, @RequestBody UpdateAccountNameRequestDto updateAccountNameRequestDto) {
		accountService.updateAccountName(updateAccountNameRequestDto, userId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
