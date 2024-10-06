package com.ssamba.petsi.notice_service.domain.notice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeResponseDto {
	private Long noticeId;
	private String category;
	private String title;
	private DateResponseDto date;
	private boolean isRead;
	private Long link;
}