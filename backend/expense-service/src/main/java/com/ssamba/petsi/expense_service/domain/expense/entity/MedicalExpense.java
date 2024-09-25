package com.ssamba.petsi.expense_service.domain.expense.entity;

import com.ssamba.petsi.expense_service.domain.expense.dto.request.MedicalExpenseUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "medical_expense")
@NoArgsConstructor
@AllArgsConstructor
public class MedicalExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicalExpenseId;

    @Column(nullable = false)
    private Long petId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String diseaseName;

    @Column(nullable = false)
    private Integer cost;

    @Column(nullable = false)
    private String hospital;

    @Column(nullable = false)
    private LocalDate visitedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private String memo;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEntity(MedicalExpenseUpdateDto medicalExpenseUpdateDto) {
        this.diseaseName = medicalExpenseUpdateDto.getDiseaseName();
        this.cost = medicalExpenseUpdateDto.getCost();
        this.hospital = medicalExpenseUpdateDto.getHospital();
        this.visitedAt = medicalExpenseUpdateDto.getVisitedAt();
    }
}
