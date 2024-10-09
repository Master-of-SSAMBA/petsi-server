package com.ssamba.petsi.schedule_service.domain.schedule.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssamba.petsi.schedule_service.domain.schedule.entity.Schedule;
import com.ssamba.petsi.schedule_service.domain.schedule.repository.ScheduleRepository;
import com.ssamba.petsi.schedule_service.global.dto.NotificationProducerDto;
import com.ssamba.petsi.schedule_service.global.kafka.KafkaProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleSchedulerService {

	private final ScheduleRepository scheduleRepository;
	private final KafkaProducer kafkaProducer;

	@Scheduled(cron = "0 0 19 * * *")
	void sendNotification() {
		scheduleRepository.findAllByNextScheduleDate(LocalDate.now().plusDays(1)).forEach(schedule -> {
			kafkaProducer.send(NotificationProducerDto.toNoticeProducerDto(schedule));
		});
	}
}
