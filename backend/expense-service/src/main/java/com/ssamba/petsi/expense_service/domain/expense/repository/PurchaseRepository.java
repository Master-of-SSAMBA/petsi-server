package com.ssamba.petsi.expense_service.domain.expense.repository;

import com.ssamba.petsi.expense_service.domain.expense.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
