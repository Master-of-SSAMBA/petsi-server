package com.ssamba.petsi.pet_service.domain.pet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetCustomDto {
	private Long petId;
	private String petName;

	public static PetCustomDto fromResponseDto(PetResponseDto petResponseDto) {
		return new PetCustomDto(petResponseDto.getPetId(), petResponseDto.getName());
	}
}
