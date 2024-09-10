package com.ssamba.petsi.account_service.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssamba.petsi.account_service.domain.account.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
