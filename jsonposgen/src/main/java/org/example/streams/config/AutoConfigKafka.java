package org.example.streams.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoConfigKafka {

    //auto create topic in kafka
    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("pos-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
