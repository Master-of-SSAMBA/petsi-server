package com.ssamba.petsi.expense_service.domain.expense.dto.response;

import com.ssamba.petsi.expense_service.domain.expense.entity.MedicalExpense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalExpenseResponseDto implements Expense {

    private Long medicalExpenseId;
    private Long petId;
    private String petName;
    private String diseaseName;
    private Integer cost;
    private String hospital;
    private LocalDate visitedAt;
    private String memo;
    private String kind;

    @Override
    public LocalDate getDate() {
        return visitedAt;
    }

    public static MedicalExpenseResponseDto fromEntity(MedicalExpense expense) {
        return MedicalExpenseResponseDto.builder()
                .medicalExpenseId(expense.getMedicalExpenseId())
                .petId(expense.getPetId())
                .diseaseName(expense.getDiseaseName())
                .cost(expense.getCost())
                .hospital(expense.getHospital())
                .visitedAt(expense.getVisitedAt())
                .memo(expense.getMemo())
                .kind("의료비")
                .build();
    }

    public static MedicalExpenseResponseDto fromEntity(MedicalExpense expense, String petName) {
        return MedicalExpenseResponseDto.builder()
                .medicalExpenseId(expense.getMedicalExpenseId())
                .petId(expense.getPetId())
                .petName(petName)
                .diseaseName(expense.getDiseaseName())
                .cost(expense.getCost())
                .hospital(expense.getHospital())
                .visitedAt(expense.getVisitedAt())
                .memo(expense.getMemo())
                .kind("의료비")
                .build();
    }

    @Override
    public String toString() {
        return "MedicalExpenseResponseDto{" +
                "medicalExpenseId=" + medicalExpenseId +
                ", petId=" + petId +
                ", petName='" + petName + '\'' +
                ", diseaseName='" + diseaseName + '\'' +
                ", cost=" + cost +
                ", hospital='" + hospital + '\'' +
                ", visitedAt=" + visitedAt +
                ", memo='" + memo + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }
}
