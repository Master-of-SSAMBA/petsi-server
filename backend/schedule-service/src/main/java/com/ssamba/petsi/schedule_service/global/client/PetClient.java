package com.ssamba.petsi.schedule_service.global.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssamba.petsi.schedule_service.global.dto.PetCustomDto;

@FeignClient(name = "pet-service")
public interface PetClient {

	@GetMapping("/api/v1/pet/{userId}")
	List<PetCustomDto> findAll(@PathVariable Long userId);

	@PostMapping("/api/v1/pet/{userId}/getpets")
	List<PetCustomDto> findPetCustomDtoById(@PathVariable Long userId, @RequestBody List<Long> pets);
}
