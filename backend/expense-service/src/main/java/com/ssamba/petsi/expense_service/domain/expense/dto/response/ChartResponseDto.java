package com.ssamba.petsi.expense_service.domain.expense.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChartResponseDto {

    private String nickname;
    private String img;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long total;
    private ChartElement costs;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChartElement {
        List<String> labels;
        List<Long> datas;
        List<Long> rates;
    }

}

