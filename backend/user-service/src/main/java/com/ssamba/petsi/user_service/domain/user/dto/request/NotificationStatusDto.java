package com.ssamba.petsi.user_service.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotificationStatusDto {
	@NotNull
	private Long userId;
	@NotNull
	private Boolean isActive;
}
