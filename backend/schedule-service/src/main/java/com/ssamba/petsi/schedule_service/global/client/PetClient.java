package com.ssamba.petsi.schedule_service.global.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssamba.petsi.schedule_service.global.dto.PetCustomDto;

@FeignClient(name = "pet-service")
public interface PetClient {
	@GetMapping("/pets/schedule/{scheduleId}")
	List<PetCustomDto> getPetsById(@RequestBody List<Long> petId);

	@GetMapping
	List<PetCustomDto> findAll(@RequestHeader("X-User-Id") Long userId);
}
