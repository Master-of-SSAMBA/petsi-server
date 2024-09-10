package com.ssamba.petsi.account_service.domain.account.dto.response;

import com.ssamba.petsi.account_service.domain.account.entity.AccountProduct;

import lombok.Getter;

@Getter
public class GetAllProductsResponseDto {
	private Long accountProductId;
	private String title;
	private int cycle;
	private String detail;
	private int minDepositAmount;
	private int maxDepositAmount;
	private double defaultInterestRate;
	private double maxInterestRate;

	private GetAllProductsResponseDto(AccountProduct accountProduct) {
		this.accountProductId = accountProduct.getAccountProductId();
		this.title = accountProduct.getTitle();
		this.cycle = accountProduct.getCycle();
		this.detail = accountProduct.getDetail();
		this.minDepositAmount = accountProduct.getMinDepositAmount();
		this.maxDepositAmount = accountProduct.getMaxDepositAmount();
		this.defaultInterestRate = accountProduct.getDefaultInterestRate();
		this.maxInterestRate = accountProduct.getMaxInterestRate();
	}

	public static GetAllProductsResponseDto from(AccountProduct accountProduct) {
		return new GetAllProductsResponseDto(accountProduct);
	}
}
