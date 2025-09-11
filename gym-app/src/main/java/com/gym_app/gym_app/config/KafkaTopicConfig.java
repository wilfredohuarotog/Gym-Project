package com.gym_app.gym_app.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic newTopic (){
        return TopicBuilder
                .name("new-member")
                .partitions(2)
                .replicas(2)
                .build();
    }
}
