package com.ssamba.petsi.pet_service.domain.pet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssamba.petsi.pet_service.domain.pet.dto.request.PetCreateRequestDto;
import com.ssamba.petsi.pet_service.domain.pet.dto.request.PetUpdateRequestDto;
import com.ssamba.petsi.pet_service.domain.pet.dto.response.PetCustomDto;
import com.ssamba.petsi.pet_service.domain.pet.dto.response.PetResponseDto;
import com.ssamba.petsi.pet_service.domain.pet.service.PetService;
import com.ssamba.petsi.pet_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.pet_service.global.exception.ExceptionCode;
import com.ssamba.petsi.pet_service.global.mapper.ValidatingObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pet")
@Tag(name = "PetRestController", description = "반려동물 관리")
public class PetController {

    private final PetService petService;
    private final ValidatingObjectMapper validatingObjectMapper;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("X-User-Id") Long userId) {
        List<PetResponseDto> pets = petService.getPets(userId);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<?> findPet(@RequestHeader("X-User-Id") Long userId, @PathVariable("petId") Long petId) {
        PetResponseDto pet = petService.getPet(userId, petId);
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    public ResponseEntity<?> createPet(@RequestHeader("X-User-Id") Long userId,
                                       @RequestPart("reqDto") String reqDto,
                                       @RequestPart(value = "image", required = false) MultipartFile image) {
        // JSON 데이터를 객체로 변환
        PetCreateRequestDto petDto;
        try {
            petDto = validatingObjectMapper.readAndValidate(reqDto, PetCreateRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_DATA_FORMAT);
        } catch (ValidatingObjectMapper.ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        petService.savePet(userId, petDto, image);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<?> updatePet(@RequestHeader("X-User-Id") Long userId,
                                       @RequestPart("reqDto") String reqDto,
                                       @RequestPart(value = "image", required = false) MultipartFile image) {
        // JSON 데이터를 객체로 변환
        PetUpdateRequestDto petDto;
        try {
            petDto = validatingObjectMapper.readAndValidate(reqDto, PetUpdateRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_DATA_FORMAT);
        } catch (ValidatingObjectMapper.ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        petService.updatePet(userId, petDto, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deletePet(@RequestHeader("X-User-Id") Long userId, @RequestBody Map<String, Long> reqDto) {
        long petId = reqDto.get("petId");

        petService.deletePet(userId, petId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/find-all-by-user-id")
    List<PetCustomDto> findAllWithPetCustomDto(@RequestBody Long userId) {
        return petService.getPets(userId).stream().map(PetCustomDto::fromResponseDto).toList();
    }

    @PostMapping("/find-pets-by-pet-id/{userId}")
    List<PetCustomDto> findPetCustomDtoById(@PathVariable Long userId, @RequestBody List<Long> pets) {
        return pets.stream().map(id -> petService.getPet(userId, id)).map(PetCustomDto::fromResponseDto).toList();
    }

    @GetMapping("/find-pets-for-user-info/{userId}")
    List<PetResponseDto> findPetForUserInfo(@PathVariable Long userId) {
        return petService.getPets(userId);
    }

    @GetMapping("/find-by-pet-id")
    PetCustomDto findPetInfo(@RequestParam Long petId) {
        return petService.getPetInfo(petId);
    }


}
