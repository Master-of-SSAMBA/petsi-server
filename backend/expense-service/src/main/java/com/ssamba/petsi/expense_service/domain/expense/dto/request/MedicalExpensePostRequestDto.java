package com.ssamba.petsi.expense_service.domain.expense.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ssamba.petsi.expense_service.domain.expense.entity.MedicalExpense;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalExpensePostRequestDto {

    @NotNull
    private Long petId;

    @NotBlank
    private String diseaseName;

    @NotNull
    private Integer cost;

    @NotBlank
    private String hospital;

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate visitedAt;

    private String memo;

    public MedicalExpense convertToMedicalExpense(Long userId) {
        return MedicalExpense.builder()
                .petId(this.getPetId())
                .userId(userId)
                .diseaseName(this.getDiseaseName())
                .cost(this.getCost())
                .hospital(this.getHospital())
                .visitedAt(this.getVisitedAt())
                .memo(this.getMemo())
                .build();
    }

}
