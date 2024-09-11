package com.ssamba.petsi.account_service.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssamba.petsi.account_service.domain.account.entity.LinkedAccount;

public interface LinkedAccountRepository extends JpaRepository<LinkedAccount, Long> {
}
