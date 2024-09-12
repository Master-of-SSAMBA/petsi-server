package com.ssamba.petsi.pet_service.domain.pet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PetRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private LocalDate birthDate;


}
