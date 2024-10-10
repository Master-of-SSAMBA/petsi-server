package com.ssamba.petsi.user_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationProducerDto {
	private Long userId;
	private String category;
	private String content;
	private Long id;

	public static NotificationProducerDto toNoticeProducerDto(String nickname, Long userId) {
		return new NotificationProducerDto(
			userId,
			"upload-picture",
			nickname+"님, 오늘 사진 인증 하셨나요?",
			null);
	}

}
