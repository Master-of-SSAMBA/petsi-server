package com.ssamba.petsi.schedule_service.global.client;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

	@PostMapping("/api/v1/pet/find-all-by-user-id")
	List<PetCustomDto> findAllWithPetCustomDto(@RequestBody Long userId);

	@PostMapping("/api/v1/pet/find-pets-by-pet-id/{userId}")
	List<PetCustomDto> findPetCustomDtoById(@PathVariable Long userId, @RequestBody List<Long> pets);
}
