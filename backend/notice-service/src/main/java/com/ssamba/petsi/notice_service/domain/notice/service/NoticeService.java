package com.ssamba.petsi.notice_service.domain.notice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamba.petsi.notice_service.domain.notice.dto.response.NoticeResponseDto;
import com.ssamba.petsi.notice_service.domain.notice.entity.Notice;
import com.ssamba.petsi.notice_service.domain.notice.repository.NoticeRepository;
import com.ssamba.petsi.notice_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.notice_service.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

	private final NoticeRepository noticeRepository;

	@Transactional(readOnly = true)
	public int getUnreadNoticeCount(Long userId) {
		return noticeRepository.countByUserIdAndIsRead(userId, false);
	}

	@Transactional(readOnly = true)
	public List<NoticeResponseDto> getAllNotice(Long userId) {
		return noticeRepository.findAllByUserIdWithCustomOrder(userId)
			.stream()
			.map(NoticeResponseDto::fromEntity)
			.toList();
	}

	public int deleteAllNotice(Long userId, boolean option) {
		Optional.of(option)
			.ifPresentOrElse(
				opt -> noticeRepository.deleteAllByUserIdAndIsRead(userId, option),
				() -> noticeRepository.deleteAllByUserId(userId)
			);

		return getUnreadNoticeCount(userId);

	}

	public int deleteSpecificNotice(Long userId, List<Long> noticeIds) {
		noticeIds.forEach(noticeRepository::deleteById);
		return getUnreadNoticeCount(userId);
	}

	public int readNotice(Long userId, Long noticeId) {
		Notice notice = noticeRepository.findByUserIdAndNoticeId(userId, noticeId).orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.NOTICE_NOT_FOUND)
		);
		notice.setRead(true);
		return getUnreadNoticeCount(userId);
	}
}
