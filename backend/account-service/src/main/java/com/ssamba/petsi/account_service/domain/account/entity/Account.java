package com.ssamba.petsi.account_service.domain.account.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.ssamba.petsi.account_service.domain.account.enums.AccountStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", nullable = false)
	private Long accountId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String userKey;

	@Column(nullable = false, unique = true)
	private String accountNo;

	@Setter
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Double interestRate;

	@Column(nullable = false)
	private Long balance;

	@Column(nullable = false)
	private String password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_product_id", nullable = false)
	private AccountProduct accountProduct;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	private LinkedAccount linkedAccount;

	@Setter
	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	private RecurringTransaction recurringTransaction;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Column(nullable = false)
	private LocalDate maturityDate;

	@Setter
	@Column(nullable = false)
	private String status;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<PetToAccount> petToAccounts;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.balance = 0L;
		this.interestRate = 1.0;
		this.status = AccountStatus.ACTIVATED.getValue();
	}

}
