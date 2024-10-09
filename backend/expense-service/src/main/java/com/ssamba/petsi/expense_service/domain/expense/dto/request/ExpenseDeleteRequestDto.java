package com.ssamba.petsi.expense_service.domain.expense.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDeleteRequestDto {
    @NotNull
    private List<Long> purchases;
    @NotNull
    private List<Long> medicalExpenses;
}
