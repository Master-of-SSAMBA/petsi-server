package com.ssamba.petsi.pet_service.domain.pet.repository;

import com.ssamba.petsi.pet_service.domain.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUserId(Long userId);

    Optional<Pet> findByPetId(Long petId);
}
