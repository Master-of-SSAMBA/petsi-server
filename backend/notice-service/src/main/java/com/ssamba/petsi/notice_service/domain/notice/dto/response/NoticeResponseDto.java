package com.ssamba.petsi.notice_service.domain.notice.dto.response;

import com.ssamba.petsi.notice_service.domain.notice.entity.Notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeResponseDto {
	private Long noticeId;
	private String category;
	private String content;
	private DateResponseDto date;
	private boolean isRead;
	private Long link;

	public static NoticeResponseDto fromEntity(Notice notice) {
		return new NoticeResponseDto(notice.getNoticeId(), notice.getCategory(), notice.getContent(),
			new DateResponseDto(notice.getCreatedAt()), notice.isRead(), notice.getLink());
	}
}