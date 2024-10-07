package com.ssamba.petsi.expense_service.domain.expense.repository;

import com.ssamba.petsi.expense_service.domain.expense.dto.response.PurchaseSumDto;
import com.ssamba.petsi.expense_service.domain.expense.entity.Purchase;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserIdAndPurchasedAtBetween(Long userId, LocalDate startDate, LocalDate endDate);

    List<Purchase> findByUserId(Long userId);

    @Query("SELECT new com.ssamba.petsi.expense_service.domain.expense.dto.response.PurchaseSumDto(p.category, SUM(p.cost)) " +
            "FROM Purchase p WHERE p.userId = :userId AND p.purchasedAt BETWEEN :startDate AND :endDate GROUP BY p.category")
    List<PurchaseSumDto> getSumByCategory(@Param("userId") Long userId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
}
