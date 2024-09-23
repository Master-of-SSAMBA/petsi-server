package com.ssamba.petsi.schedule_service.domain.schedule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssamba.petsi.schedule_service.domain.schedule.service.ScheduleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
@Tag(name = "ScheduleController", description = "일정 컨트롤러")
public class ScheduleController {

	private final ScheduleService scheduleService;

}
