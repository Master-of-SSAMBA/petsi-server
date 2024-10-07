package com.ssamba.petsi.expense_service.domain.expense.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MarketLoginDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
