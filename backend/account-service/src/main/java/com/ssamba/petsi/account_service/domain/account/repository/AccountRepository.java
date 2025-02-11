package com.ssamba.petsi.account_service.domain.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssamba.petsi.account_service.domain.account.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findAllByUserIdAndStatus(Long userId, String status);

	@Query("SELECT a FROM Account a LEFT JOIN FETCH a.recurringTransaction WHERE a.accountId = :accountId")
	Optional<Account> findByIdWithRecurringTransaction(@Param("accountId") Long accountId);

	Optional<Account> findByAccountIdAndStatusAndUserId(Long accountId, String status, Long userId);

	Optional<Account> findByAccountIdAndStatusAndUserIdAndAccountNo(Long accountId, String value, Long userId, String accountNo);

	List<Account> findAllByStatus(String value);
}
