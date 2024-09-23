package com.ssamba.petsi.expense_service.domain.expense.repository;

import com.ssamba.petsi.expense_service.domain.expense.entity.MedicalExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalExpenseRepository extends JpaRepository<MedicalExpense, Long> {
}
