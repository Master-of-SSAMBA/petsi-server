package com.ssamba.petsi.notice_service.domain.notice.dto.request;

import com.ssamba.petsi.notice_service.domain.notice.entity.UserToken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenRequestDto {
	private String token;

	public UserToken toEntity(Long userId, TokenRequestDto dto) {
		return UserToken.builder()
			.token(token)
			.userId(userId)
			.build();
	}
}
