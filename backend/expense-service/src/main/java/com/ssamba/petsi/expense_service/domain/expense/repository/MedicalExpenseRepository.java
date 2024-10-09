package com.ssamba.petsi.expense_service.domain.expense.repository;

import com.ssamba.petsi.expense_service.domain.expense.entity.MedicalExpense;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MedicalExpenseRepository extends JpaRepository<MedicalExpense, Long> {
    List<MedicalExpense> findByUserIdAndVisitedAtBetween(Long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(me.cost), 0) FROM MedicalExpense me WHERE me.visitedAt BETWEEN :startDate AND :endDate")
    long sumCostByBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    void deleteByUserIdAndMedicalExpenseIdIn(Long userId, List<Long> medicalExpenses);

    void deleteByUserIdAndMedicalExpenseId(Long userId, long medicalExpenseId);
}
