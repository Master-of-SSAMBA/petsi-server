package com.ssamba.petsi.user_service.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.ssamba.petsi.user_service.global.dto.PetResponseDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetUserInfoResponseDto {
	private long petId;
	private String name;
	private LocalDate birthDate;
	private int year;
	private int month;
	private String species;
	private String breed;
	private String gender;
	private String image;
	private double weight;

	public PetUserInfoResponseDto(PetResponseDto petResponseDto) {
		this.petId = petResponseDto.getPetId();
		this.name = petResponseDto.getName();
		this.birthDate = petResponseDto.getBirthDate();
		this.species = petResponseDto.getSpecies();
		this.breed = petResponseDto.getBreed();
		this.gender = petResponseDto.getGender();
		this.image = petResponseDto.getImage();
		this.weight = petResponseDto.getWeight();        // year, month 계산
		long[] age = calAge(this.birthDate);
		this.year = (int) age[0];
		this.month = (int) age[1];
	}

	// 나이 계산 메서드
	private long[] calAge(LocalDate birthDate) {
		LocalDate today = LocalDate.now();
		long monthsBetween = ChronoUnit.MONTHS.between(birthDate, today);

		long year = monthsBetween / 12;
		long month = monthsBetween % 12;
		if (year == 0 && month == 0) month = 1;

		return new long[]{year, month}; // year과 month를 배열로 반환
	}

}
