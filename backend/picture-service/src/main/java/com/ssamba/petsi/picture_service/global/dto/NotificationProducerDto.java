package com.ssamba.petsi.picture_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationProducerDto {
	private Long userId;
	private String category;
	private String content;
	private Long id;

	public static NotificationProducerDto toNoticeProducerDto(int pictureCnt, Long userId) {
		return new NotificationProducerDto(
			userId,
			"streak",
			"이번 달 " + pictureCnt + "일 째 인증 중이에요! 계속 이어나가세요.",
			null);
	}

}
