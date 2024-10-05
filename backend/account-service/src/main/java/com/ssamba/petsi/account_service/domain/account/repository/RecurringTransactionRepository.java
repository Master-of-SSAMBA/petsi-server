package com.ssamba.petsi.account_service.domain.account.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssamba.petsi.account_service.domain.account.entity.RecurringTransaction;

public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
	List<RecurringTransaction> findAllByStatusNotAndNextTransactionDateLessThanEqual(String value, LocalDate now);
}
