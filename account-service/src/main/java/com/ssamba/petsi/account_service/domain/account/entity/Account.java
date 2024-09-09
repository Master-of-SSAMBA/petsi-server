package com.ssamba.petsi.account_service.domain.account.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.ssamba.petsi.account_service.domain.account.enums.AccountStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="account")
@Getter
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long accountId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private Long accountProductId;

	@Column(nullable = false, unique = true)
	private String accountNo;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Double interestRate;

	@Column(nullable = false)
	private Long balance;

	@Column(nullable = false)
	private String password;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Column(nullable = false)
	private LocalDate maturityDate;

	@Column(nullable = false)
	private String status;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.balance = 0L;
		this.interestRate = 1.0;
		this.status = AccountStatus.ACTIVATED.getValue();
	}

}
