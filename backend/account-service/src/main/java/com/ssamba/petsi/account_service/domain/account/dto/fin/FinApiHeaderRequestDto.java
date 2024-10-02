package com.ssamba.petsi.account_service.domain.account.dto.fin;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FinApiHeaderRequestDto {

	private static int seq = 1;

	private String apiName;
	private String transmissionDate;
	private String transmissionTime;
	private final String institutionCode = "00100";
	private final String fintechAppNo = "001";

	private String apiServiceCode;
	private String institutionTransactionUniqueNo;
	@Setter
	private String apiKey;
	@Setter
	private String userKey;

	public FinApiHeaderRequestDto(String apiName, String apiServiceCode) {
		String[] timeUnits = LocalDateTime.now().toString().split("T");
		String date = timeUnits[0].replaceAll("-", "");
		String time = timeUnits[1].replaceAll(":", "").replaceAll("\\.\\d+", "");
		this.apiName = apiName;
		this.apiServiceCode = apiServiceCode;
		this.transmissionDate = date;
		this.transmissionTime = time;
		String iTUN = "000000" + Integer.toString(seq++);
		iTUN = iTUN.substring(iTUN.length() - 6);
		this.institutionTransactionUniqueNo = date + time + iTUN;

		if (seq >= 990000) {
			seq = 0;
		}
	}
}
