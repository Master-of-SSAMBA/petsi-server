package com.ssamba.petsi.expense_service.domain.expense.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseSumDto implements Comparable<PurchaseSumDto> {

    private String category;
    private long sum;

    @Override
    public int compareTo(PurchaseSumDto o) {
        if(o.sum == this.sum) return this.category.compareTo(o.category);
        return Long.compare(o.sum, this.sum);
    }

}
