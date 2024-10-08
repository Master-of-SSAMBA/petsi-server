package com.ssamba.petsi.picture_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeProducerDto<T> {
	private Long userId;
	private T content;
	private Long id;

	public static NoticeProducerDto<Long> toNoticeProducerDto(Long pictureCnt, Long userId) {
		return new NoticeProducerDto<Long>(
			userId,
			pictureCnt,
			null);
	}

}
