package com.ssamba.petsi.expense_service.domain.expense.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyExpenseResponseDto {

    private String nickname;
    private String img;
    private int month;
    private Long total;
    private List<PurchaseSumDto> costs;

}

