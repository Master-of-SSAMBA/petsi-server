package com.ssamba.petsi.expense_service.global.client;

import com.ssamba.petsi.expense_service.domain.expense.dto.response.PetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pet-service")
public interface PetClient {

    @GetMapping("/api/v1/pet/find-by-pet-id")
    PetDto findPetInfo(@RequestParam Long petId);

}
