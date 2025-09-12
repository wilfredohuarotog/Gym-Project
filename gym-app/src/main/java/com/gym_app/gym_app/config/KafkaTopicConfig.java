package com.gym_app.gym_app.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic newMemberTopic() {
        return TopicBuilder
                .name("new-member")
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic membershipExpiredTopic() {
        return TopicBuilder
                .name("membership-expired")
                .partitions(2)
                .replicas(1)
                .build();
    }

}
