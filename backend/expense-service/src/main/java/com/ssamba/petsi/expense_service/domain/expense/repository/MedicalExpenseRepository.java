package com.ssamba.petsi.expense_service.domain.expense.repository;

import com.ssamba.petsi.expense_service.domain.expense.entity.MedicalExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicalExpenseRepository extends JpaRepository<MedicalExpense, Long> {
    List<MedicalExpense> findByUserIdAndVisitedAtBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
