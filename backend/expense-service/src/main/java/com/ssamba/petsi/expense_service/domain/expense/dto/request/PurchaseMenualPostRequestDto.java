package com.ssamba.petsi.expense_service.domain.expense.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ssamba.petsi.expense_service.domain.expense.entity.Purchase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseMenualPostRequestDto {

    @NotBlank
    private String title;

    private String detail;

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate purchasedAt;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer cost;

    @NotBlank
    private String category;

    public Purchase convertToPurchase(Long userId, String url) {
        return Purchase.builder()
                .userId(userId)
                .title(this.title)
                .detail(this.detail)
                .img(url)
                .purchasedAt(this.purchasedAt)
                .quantity(this.quantity)
                .cost(this.cost)
                .category(this.category)
                .build();
    }

    public Purchase convertToPurchase(Long userId) {
        return Purchase.builder()
                .userId(userId)
                .title(this.title)
                .detail(this.detail)
                .purchasedAt(this.purchasedAt)
                .quantity(this.quantity)
                .cost(this.cost)
                .category(this.category)
                .build();
    }
}
