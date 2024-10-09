package com.ssamba.petsi.expense_service.domain.expense.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseSumDto implements Comparable<PurchaseSumDto> {

    private String labels;
    private long data;

    @Override
    public int compareTo(PurchaseSumDto o) {
        if(o.data == this.data) return this.labels.compareTo(o.labels);
        return Long.compare(o.data, this.data);
    }

}
