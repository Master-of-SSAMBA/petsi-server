package com.ssamba.petsi.expense_service.domain.expense.dto.response;

import com.ssamba.petsi.expense_service.domain.expense.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseDto implements Expense {
    private Long purchaseId;
    private String title;
    private String img;
    private String option;
    private LocalDate purchasedAt;
    private Integer quantity;
    private Integer cost;
    private String category;
    private String kind;

    @Override
    public LocalDate getDate() {
        return purchasedAt;
    }

    @Override
    public String toString() {
        return "PurchaseResponseDto{" +
                "purchaseId=" + purchaseId +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", option='" + option + '\'' +
                ", purchasedAt=" + purchasedAt +
                ", quantity=" + quantity +
                ", cost=" + cost +
                ", category='" + category + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }

    public static PurchaseResponseDto fromEntity(Purchase purchase) {
        return PurchaseResponseDto.builder()
                .purchaseId(purchase.getPurchaseId())
                .title(purchase.getTitle())
                // 프론트에서 쓰는 img 디폴트 경로로 바꾸어야 함, 현재는 임시
                .img(purchase.getImg())
                .option(Optional.ofNullable(purchase.getDetail()).orElse(""))
                .purchasedAt(purchase.getPurchasedAt())
                .quantity(purchase.getQuantity())
                .cost(purchase.getCost())
                .category(purchase.getCategory())
                .kind("구매목록")
                .build();
    }

}
