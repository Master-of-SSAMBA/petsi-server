package com.ssamba.petsi.notice_service.domain.notice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssamba.petsi.notice_service.domain.notice.dto.request.TokenRequestDto;
import com.ssamba.petsi.notice_service.domain.notice.service.NoticeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
@Tag(name = "NoticeController", description = "알림 컨트롤러")
public class NoticeController {

	private final NoticeService noticeService;

	@PostMapping("/fcm")
	@Operation(summary = "토큰 저장하기")
	public ResponseEntity<?> saveToken(@RequestHeader("X-User-Id") Long userId, @RequestBody TokenRequestDto dto) {
		noticeService.saveToken(userId, dto);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@DeleteMapping("/fcm")
	@Operation(summary = "토큰 전체 삭제")
	public ResponseEntity<?> deleteAllTokens(@RequestHeader("X-User-Id") Long userId) {
		noticeService.deleteAllTokens(userId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}


	@DeleteMapping("/fcm-one")
	@Operation(summary = "단일 토큰 삭제")
	public ResponseEntity<?> deleteToken(@RequestHeader("X-User-Id") Long userId, @RequestBody TokenRequestDto dto) {
		noticeService.deleteToken(userId, dto);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("")
	@Operation(summary = "전체 알림 불러오기")
	public ResponseEntity<?> getAllNotice(@RequestHeader("X-User-Id") Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(noticeService.getAllNotice(userId));
	}

	@DeleteMapping("")
	@Operation(summary = "전체 알림 삭제하기")
	public ResponseEntity<?> deleteAllNotice(@RequestHeader("X-User-Id") Long userId,
		@RequestParam(value = "option", required = false) boolean option) {
		return ResponseEntity.status(HttpStatus.OK).body(noticeService.deleteAllNotice(userId, option));
	}

	@DeleteMapping("/delete")
	@Operation(summary = "특정 id의 알림 삭제하기")
	public ResponseEntity<?> deleteNotice(@RequestHeader("X-User-Id") Long userId, @RequestBody List<Long> noticeIds) {
		return ResponseEntity.status(HttpStatus.OK).body(noticeService.deleteSpecificNotice(userId, noticeIds));
	}

	@PatchMapping("/{noticeId}")
	@Operation(summary = "특정 id의 알림 읽음처리하기")
	public ResponseEntity<?> readNotice(@RequestHeader("X-User-Id") Long userId, @PathVariable Long noticeId) {
		return ResponseEntity.status(HttpStatus.OK).body(noticeService.readNotice(userId, noticeId));

	}

}
