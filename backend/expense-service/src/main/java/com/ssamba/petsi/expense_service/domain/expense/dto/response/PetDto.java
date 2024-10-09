package com.ssamba.petsi.expense_service.domain.expense.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {
    private Long petId;
    private String petName;
    private String petImg;
}
