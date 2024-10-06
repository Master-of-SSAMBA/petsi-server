package com.ssamba.petsi.user_service.global.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ssamba.petsi.user_service.global.dto.PetCustomDto;

@FeignClient(name = "pet-service")
public interface PetClient {

	@PostMapping("/api/v1/pet/find-all-by-user-id")
	List<PetCustomDto> findAllWithPetCustomDto(@RequestBody Long userId);

	@PostMapping("/api/v1/pet/find-pets-by-pet-id/{userId}")
	List<PetCustomDto> findPetCustomDtoById(@PathVariable Long userId, @RequestBody List<Long> pets);
}
