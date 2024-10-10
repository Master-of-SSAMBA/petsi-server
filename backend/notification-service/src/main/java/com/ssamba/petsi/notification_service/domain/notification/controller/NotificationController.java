package com.ssamba.petsi.notification_service.domain.notification.controller;

import java.util.List;

import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationConsumerDto;
import com.ssamba.petsi.notification_service.domain.notification.dto.request.TokenRequestDto;
import com.ssamba.petsi.notification_service.domain.notification.service.FirebaseService;
import com.ssamba.petsi.notification_service.domain.notification.service.NotificationService;
import com.ssamba.petsi.notification_service.domain.notification.service.TokenService;

import org.checkerframework.checker.units.qual.N;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@Tag(name = "NotificationController", description = "알림 컨트롤러")
public class NotificationController {

	private final NotificationService notificationService;
	private final TokenService tokenService;
	private final FirebaseService firebaseService;

	@PostMapping("/fcm")
	@Operation(summary = "토큰 저장하기")
	public ResponseEntity<?> saveToken(@RequestHeader("X-User-Id") Long userId, @RequestBody TokenRequestDto dto) {
		tokenService.saveToken(userId, dto);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@DeleteMapping("/fcm")
	@Operation(summary = "토큰 전체 삭제")
	public ResponseEntity<?> deleteAllTokens(@RequestHeader("X-User-Id") Long userId) {
		tokenService.deleteAllTokens(userId);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}


	@DeleteMapping("/fcm-one")
	@Operation(summary = "단일 토큰 삭제")
	public ResponseEntity<?> deleteToken(@RequestHeader("X-User-Id") Long userId, @RequestBody TokenRequestDto dto) {
		tokenService.deleteToken(userId, dto);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("")
	@Operation(summary = "전체 알림 불러오기")
	public ResponseEntity<?> getAllNotification(@RequestHeader("X-User-Id") Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(notificationService.getAllNotification(userId));
	}

	@DeleteMapping("")
	@Operation(summary = "전체 알림 삭제하기")
	public ResponseEntity<?> deleteAllNotification(@RequestHeader("X-User-Id") Long userId,
		@RequestParam(value = "option", required = false) boolean option) {
		return ResponseEntity.status(HttpStatus.OK).body(notificationService.deleteAllNotification(userId, option));
	}

	@DeleteMapping("/delete")
	@Operation(summary = "특정 id의 알림 삭제하기")
	public ResponseEntity<?> deleteNotification(@RequestHeader("X-User-Id") Long userId, @RequestBody List<Long> notificationIds) {
		return ResponseEntity.status(HttpStatus.OK).body(notificationService.deleteSpecificNotification(userId, notificationIds));
	}

	@PatchMapping("/{notificationId}")
	@Operation(summary = "특정 id의 알림 읽음처리하기")
	public ResponseEntity<?> readNotification(@RequestHeader("X-User-Id") Long userId, @PathVariable Long notificationId) {
		return ResponseEntity.status(HttpStatus.OK).body(notificationService.readNotification(userId, notificationId));

	}

	@GetMapping("/test")
	public void test() {
		firebaseService.sendPushNotification(new NotificationConsumerDto(1L, "upload-picture", "다환님, 오늘 사진 인증 하셨나요?", null));
	}


}
