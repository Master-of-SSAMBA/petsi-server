package com.ssamba.petsi.schedule_service.domain.schedule.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.CreateScheduleRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.UpdateScheduleCategoryRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.service.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
@Tag(name = "ScheduleController", description = "일정 컨트롤러")
public class ScheduleController {

	private final ScheduleService scheduleService;

	@GetMapping("/category")
	@Operation(summary = "일정 카테고리 불러오기")
	public ResponseEntity<?> getScheduleCategory(@RequestHeader("X-User-Id") Long userId) {
		List<?> list = scheduleService.getScheduleCategory(userId);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}


	@PostMapping("/category")
	@Operation(summary = "일정 카테고리 추가하기")
	public ResponseEntity<?> addScheduleCategory(@RequestHeader("X-User-Id") Long userId, @RequestBody Map<String, String> title) {
		scheduleService.addScheduleCategory(userId, title.get("title"));
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}


	@DeleteMapping("/category")
	@Operation(summary = "일정 카테고리 삭제하기")
	public ResponseEntity<?> deleteScheduleCategory(@RequestHeader("X-User-Id") Long userId, @RequestBody Map<String, Long> scheduleCategory) {
		scheduleService.deleteScheduleCategory(userId, scheduleCategory.get("id"));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}


	@PutMapping("/category")
	@Operation(summary = "일정 카테고리 수정하기")
	public ResponseEntity<?> updateScheduleCategory(@RequestHeader("X-User-Id") Long userId,
		@RequestBody UpdateScheduleCategoryRequestDto requestDto) {
		scheduleService.updateScheduleCategory(userId, requestDto);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping()
	@Operation(summary = "월별 전체 일정 불러오기")
	public ResponseEntity<?> getSchedules(@RequestHeader("X-User-Id") Long userId, @RequestParam("month") int month, @RequestBody Map<String, Long> pet) {
		return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getSchedulesPerMonth(userId, month, pet.get("pet_id")));
	}


	@GetMapping("/category/{id}")
	@Operation(summary = "상세 일정 불러오기")
	public ResponseEntity<?> getScheduleDetail(@RequestHeader("X-User-Id") Long userId, @PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getScheduleDetail(userId, id));
	}

	@DeleteMapping("/category")
	@Operation(summary = "상세 일정 삭제하기")
	public ResponseEntity<?> deleteSchedule(@RequestHeader("X-User-Id") Long userId, @RequestBody Map<String, Long> scheduleId) {
		scheduleService.deleteSchedule(userId, scheduleId.get("id"));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}


	@PostMapping("/category")
	@Operation(summary = "상세 일정 등록하기")
	public ResponseEntity<?> createSchedule(@RequestHeader("X-User-Id") Long userId, @RequestBody
		CreateScheduleRequestDto createScheduleRequestDto) {
		scheduleService.createSchedule(userId, createScheduleRequestDto);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
