package com.ssamba.petsi.notification_service.domain.notification.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationProducerDto<T> {
    private Long userId;
    private T content;
    private Long id;

    @AllArgsConstructor
    @Getter
    public static class AccountNoticeInfo {
        private String accountName;
        private Long amount;
    }

    @AllArgsConstructor
    @Getter
    public static class PictureNoticeInfo {
        private int streakCount;
    }

    @AllArgsConstructor
    @Getter
    public static class ScheduleNoticeInfo {
        private String scheduleCategory;
        private String scheduleDetail;
    }
}
