package com.ssamba.petsi.pet_service.domain.pet.entity;

import com.ssamba.petsi.pet_service.domain.pet.dto.request.PetUpdateRequestDto;
import com.ssamba.petsi.pet_service.domain.pet.dto.response.PetResponseDto;
import com.ssamba.petsi.pet_service.domain.pet.enums.PetStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="pet")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long petId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private LocalDate birthdate;

	@Column(nullable = false)
	private String species;

	@Column(nullable = false)
	private String breed;

	@Column(nullable = false)
	private String gender;

	@Column
	private String image;

	@Column
	private Double weight;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Column(nullable = false)
	private String status;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.status = PetStatus.ACTIVATED.getValue();
	}

	public void updatePet(PetUpdateRequestDto reqDto, String image) {
		this.name = reqDto.getName();
		this.birthdate = reqDto.getBirthDate();
		this.species = reqDto.getSpecies();
		this.breed = reqDto.getBreed();
		this.gender = reqDto.getGender();
		this.image = image;
		this.weight = reqDto.getWeight();
	}

	public void deletePet() {
		this.status = PetStatus.INACTIVATED.getValue();
	}

}
