package com.ssamba.petsi.pet_service.domain.pet.dto.response;

import com.ssamba.petsi.pet_service.domain.pet.entity.Pet;
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
    private Age age;
    private String species;
    private String breed;
    private String gender;
    private String image;
    private double weight;

    public PetResponseDto(Pet pet, Age age) {
        this.petId = pet.getPetId();
        this.name = pet.getName();
        this.birthDate = pet.getBirthdate();
        this.age = age;
        this.species = pet.getSpecies();
        this.breed = pet.getBreed();
        this.gender = pet.getGender();
        this.image = pet.getImage();
        this.weight = pet.getWeight();
    }

    public static PetResponseDto fromEntity(Pet pet, Age age) {
        return new PetResponseDto(pet.getPetId(), pet.getName(), pet.getBirthdate(), age, pet.getSpecies(), pet.getBreed(), pet.getGender(), pet.getImage(), pet.getWeight());
    }

    @Getter
    @AllArgsConstructor
    public static class Age {
        private long year;
        private long month;
    }
}
