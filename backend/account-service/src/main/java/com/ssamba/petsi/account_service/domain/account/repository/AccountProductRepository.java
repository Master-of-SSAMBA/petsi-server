package com.ssamba.petsi.account_service.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssamba.petsi.account_service.domain.account.entity.AccountProduct;

public interface AccountProductRepository extends JpaRepository<AccountProduct, Long> {
}
