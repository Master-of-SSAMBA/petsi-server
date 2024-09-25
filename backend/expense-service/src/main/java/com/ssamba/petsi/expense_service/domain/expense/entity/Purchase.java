package com.ssamba.petsi.expense_service.domain.expense.entity;

import com.ssamba.petsi.expense_service.domain.expense.dto.request.PurchaseUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Builder
@Table(name = "purchase")
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String detail;

    @Column(nullable = true)
    private String img;

    @Column(nullable = false)
    private LocalDate purchasedAt;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer cost;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEntity(PurchaseUpdateDto purchaseUpdateDto) {
        this.title = purchaseUpdateDto.getTitle();
        this.detail = Optional.ofNullable(purchaseUpdateDto.getOption()).orElse("");
        this.purchasedAt = purchaseUpdateDto.getPurchasedAt();
        this.quantity = purchaseUpdateDto.getQuantity();
        this.cost = purchaseUpdateDto.getCost();
        this.category = purchaseUpdateDto.getCategory();
    }
}
