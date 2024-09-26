package com.ssamba.petsi.expense_service.domain.expense.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PredictRequestDto {
    List<PurchaseAiPostRequestDto> items;
}
