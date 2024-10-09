package com.ssamba.petsi.schedule_service.domain.schedule.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.AddScheduleCategoryRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.CreateScheduleRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.UpdateScheduleDto;
import com.ssamba.petsi.schedule_service.domain.schedule.dto.request.UpdateScheduleRequestDto;
import com.ssamba.petsi.schedule_service.domain.schedule.entity.ScheduleCategory;
import com.ssamba.petsi.schedule_service.domain.schedule.enums.ScheduleSortingStatus;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.ScheduleRepository;
import com.ssamba.petsi.schedule_service.domain.schedule.service.ScheduleCategoryService;
import com.ssamba.petsi.schedule_service.domain.schedule.service.ScheduleService;
import com.ssamba.petsi.schedule_service.global.dto.NotificationProducerDto;
import com.ssamba.petsi.schedule_service.global.kafka.KafkaProducer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
@Tag(name = "ScheduleController", description = "일정 컨트롤러")
public class ScheduleController {

	private final ScheduleService scheduleService;
	private final ScheduleCategoryService scheduleCategoryService;

	@GetMapping("/category")
	@Operation(summary = "일정 카테고리 불러오기")
	public ResponseEntity<?> getScheduleCategory(@RequestHeader("X-User-Id") Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(scheduleCategoryService.getScheduleCategory(userId));
	}


	@PostMapping("/category")
	@Operation(summary = "일정 카테고리 추가하기")
	public ResponseEntity<?> addScheduleCategory(@RequestHeader("X-User-Id") Long userId, @RequestBody
		AddScheduleCategoryRequestDto dto) {
		scheduleCategoryService.addScheduleCategory(userId, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}


	@DeleteMapping("/category")
	@Operation(summary = "일정 카테고리 삭제하기")
	public ResponseEntity<?> deleteScheduleCategory(@RequestHeader("X-User-Id") Long userId, @RequestBody Map<String, Long> scheduleCategory) {
		scheduleCategoryService.deleteScheduleCategory(userId, scheduleCategory.get("id"));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@GetMapping("/detail")
	@Operation(summary = "월별 상세 일정 불러오기")
	public ResponseEntity<?> getSchedules(@RequestHeader("X-User-Id") Long userId, @RequestParam("date") String date,
		@RequestParam(value = "petId", required = false) Long petId, @RequestParam(value = "status", required = false) String status) {
		return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getSchedules(userId, date, petId, status));
	}

	@GetMapping("/{scheduleId}")
	@Operation(summary = "상세 일정 불러오기")
	public ResponseEntity<?> getScheduleDetail(@RequestHeader("X-User-Id") Long userId,
		@PathVariable Long scheduleId) {
		return ResponseEntity.status(HttpStatus.OK).body(
			scheduleService.getScheduleDetail(userId, scheduleId));
	}

	@DeleteMapping("")
	@Operation(summary = "상세 일정 삭제하기")
	public ResponseEntity<?> deleteSchedule(@RequestHeader("X-User-Id") Long userId,
		@RequestBody Map<String, Long> scheduleId) {
		scheduleService.deleteSchedule(userId, scheduleId.get("id"));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}


	@PostMapping("")
	@Operation(summary = "상세 일정 등록하기")
	public ResponseEntity<?> createSchedule(@RequestHeader("X-User-Id") Long userId, @RequestBody
		CreateScheduleRequestDto createScheduleRequestDto) {
		ScheduleCategory category = scheduleCategoryService.findById(createScheduleRequestDto.getScheduleCategoryId());
		scheduleService.createSchedule(userId, createScheduleRequestDto, category);
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}


	@PutMapping("")
	@Operation(summary = "상세 일정 수정하기")
	public ResponseEntity<?> updateSchedule(@RequestHeader("X-User-Id") Long userId, @RequestBody
	UpdateScheduleRequestDto updateScheduleRequestDto) throws IllegalAccessException {
		scheduleService.updateSchedule(new UpdateScheduleDto(updateScheduleRequestDto,
			scheduleCategoryService.findById(updateScheduleRequestDto.getScheduleCategoryId())));
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}

	@PatchMapping("")
	@Operation(summary = "상세 일정 완료하기")
	public ResponseEntity<?> finishSchedule(@RequestHeader("X-User-Id") Long userId, @RequestBody
	Map<String, Long> scheduleId) {
		scheduleService.finishSchedule(userId, scheduleId.get("scheduleId"));
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@DeleteMapping("/ended")
	@Operation(summary = "완료한 상세 일정 삭제하기")
	public ResponseEntity<?> deleteFinishedSchedule(@RequestHeader("X-User-Id") Long userId, @RequestBody
	Map<String, Long> endedScheduleId) {
		scheduleService.deleteFinishedSchedule(userId, endedScheduleId.get("endedScheduleId"));
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
