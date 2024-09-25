package com.ssamba.petsi.expense_service.domain.expense.repository;

import com.ssamba.petsi.expense_service.domain.expense.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserIdAndPurchasedAtBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
