package com.ssamba.petsi.account_service.domain.account.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accountProduct")
@Getter
@NoArgsConstructor
public class AccountProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_product_id", nullable = false)
	private Long accountProductId;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String accountTypeUniqueNo;

	@Column(nullable = false)
	private int cycle;

	@Column(nullable = false)
	private String detail;

	@Column(nullable = false)
	private int minDepositAmount;

	@Column(nullable = false)
	private int maxDepositAmount;

	@Column(nullable = false)
	private double defaultInterestRate;

	@Column(nullable = false)
	private double maxInterestRate;

	@OneToMany(mappedBy = "accountProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Account> accounts;

}
