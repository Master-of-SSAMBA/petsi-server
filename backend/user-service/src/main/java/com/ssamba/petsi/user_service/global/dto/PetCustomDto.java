package com.ssamba.petsi.user_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetCustomDto {
	private Long petId;
	private String petName;
	private String petImg;
}
