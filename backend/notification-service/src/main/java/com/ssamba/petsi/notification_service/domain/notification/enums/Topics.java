package com.ssamba.petsi.notification_service.domain.notification.enums;

import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationConsumerDto;
import com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationProducerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.ssamba.petsi.notification_service.domain.notification.dto.kafka.NotificationProducerDto.*;

@AllArgsConstructor
@Getter
public enum Topics {
    UPLOAD_PICTURE() {
        @Override
        public NotificationConsumerDto createNotificationConsumerDto(NotificationProducerDto<?> producer) {
            PictureNoticeInfo content = (PictureNoticeInfo) producer.getContent();
            return NotificationConsumerDto.fromProducer(producer, "streak",
                    "이번 달 " + content.getStreakCount() + "일 째 인증 중이에요! 계속 이어나가세요.");
        }
    },
    TRANSFER_SUCCESS() {
        @Override
        public NotificationConsumerDto createNotificationConsumerDto(NotificationProducerDto<?> producer) {
            AccountNoticeInfo content = (AccountNoticeInfo) producer.getContent();
            return NotificationConsumerDto.fromProducer(producer, "bank",
                    content.getAccountName() + " 계좌에 " + content.getAmount() + "원이 입금되었어요.");
        }
    },
    SCHEDULE() {
        @Override
        public NotificationConsumerDto createNotificationConsumerDto(NotificationProducerDto<?> producer) {
            ScheduleNoticeInfo content = (ScheduleNoticeInfo) producer.getContent();
            return NotificationConsumerDto.fromProducer(producer, "plan",
                    "내일 " + content.getScheduleCategory() + "-" + content.getScheduleDetail() + " 일정이 예정되어 있어요");
        }
    };

    public abstract NotificationConsumerDto createNotificationConsumerDto(NotificationProducerDto<?> producer);
}
