package com.ssamba.petsi.schedule_service.global.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KafkaConfig {
	@Bean
	public NewTopic javaguidesTopic() {
		return TopicBuilder.name("java")
			.build();
	}
}