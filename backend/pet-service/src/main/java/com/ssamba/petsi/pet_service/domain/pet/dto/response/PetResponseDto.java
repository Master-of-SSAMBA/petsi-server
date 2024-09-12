package com.ssamba.petsi.pet_service.domain.pet.dto.response;

import com.ssamba.petsi.pet_service.domain.pet.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public static PetResponseDto fromEntity(Pet pet) {
        return new PetResponseDto(pet.getPetId(), pet.getName(), pet.getBirthdate(), pet.getSpecies(), pet.getBreed(), pet.getGender(), pet.getImage(), pet.getWeight());
    }
}
