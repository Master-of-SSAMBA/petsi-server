package com.ssamba.petsi.account_service.domain.account.dto.fin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinApiHeaderResponseDto {
	private String responseCode;
	private String responseMessage;
	private String apiName;
	private String transmissionDate;
	private String transmissionTime;
	private String institutionCode;
	private String apiKey;
	private String apiServiceCode;
	private String institutionTransactionUniqueNo;
}
