package com.ssamba.petsi.picture_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationProducerDto<T> {
	private Long userId;
	private T content;
	private Long id;

	public static NotificationProducerDto<Long> toNoticeProducerDto(Long pictureCnt, Long userId) {
		return new NotificationProducerDto<Long>(
			userId,
			pictureCnt,
			null);
	}

}
