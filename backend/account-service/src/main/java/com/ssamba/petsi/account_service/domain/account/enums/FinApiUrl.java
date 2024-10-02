package com.ssamba.petsi.account_service.domain.account.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum FinApiUrl {
	openAccountAuth("https://finopenapi.ssafy.io/ssafy/api/v1/edu/accountAuth/openAccountAuth"),
	checkAuthCode("https://finopenapi.ssafy.io/ssafy/api/v1/edu/accountAuth/checkAuthCode"),
	createDemandDepositAccount("https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/createDemandDepositAccount"),
	deleteDemandDepositAccount("https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/deleteDemandDepositAccount"),
	inquireTransactionHistoryList(
		"https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireTransactionHistoryList"),
	inquireDemandDepositAccountList("https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositAccountList"),
	inquireDemandDepositAccount("https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositAccount"),
	updateDemandDepositAccountTransfer("https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/updateDemandDepositAccountTransfer"),
	inquireDemandDepositAccountHolderName("https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositAccountHolderName");

	private String url;

	FinApiUrl(String url) {
		this.url = url;
	}
}
