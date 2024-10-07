package com.ssamba.petsi.user_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDto {
	private long petId;
	private String name;
	private LocalDate birthDate;
	private String species;
	private String breed;
	private String gender;
	private String image;
	private double weight;

}
