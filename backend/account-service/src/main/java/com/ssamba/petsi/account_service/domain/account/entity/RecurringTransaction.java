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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Recurring_Transaction")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long recurringTransactionId;

	@OneToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@Setter
	@Column(nullable = false)
	private Long amount;

	@Setter
	@Column(nullable = false)
	private int paymentDate;

	@Setter
	@Column(nullable = false)
	private LocalDate nextTransactionDate;

	@Setter
	@Column(nullable = false)
	private String status;
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.status = AccountStatus.ACTIVATED.getValue();
	}

}
