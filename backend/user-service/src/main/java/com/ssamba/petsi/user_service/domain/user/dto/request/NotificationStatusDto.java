package com.ssamba.petsi.user_service.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotificationStatusDto {
	private Long userId;
	private Boolean value;
}
