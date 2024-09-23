package com.ssamba.petsi.pet_service.domain.pet.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ssamba.petsi.pet_service.domain.pet.entity.Pet;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetUpdateRequestDto {

    @NotNull
    private Long petId;

    @NotBlank
    private String name;

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank
    private String species;

    @NotBlank
    private String breed;

    @NotBlank
    private String gender;

    @NotNull
    @Positive(message = "몸무게는 음수가 될 수 없습니다.")
    @Digits(integer = 2, fraction = 2, message = "몸무게는 소숫점 둘째 자리 까지만 가능합니다.")
    private Double weight;

}
