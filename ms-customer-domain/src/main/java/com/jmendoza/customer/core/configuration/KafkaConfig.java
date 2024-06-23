package com.jmendoza.customer.core.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final TopicConfigProps topicConfigProps;

    @Bean
    public NewTopic kRequests() {
        return TopicBuilder.name(topicConfigProps.getFindCustomerRequest().getName())
                .partitions(topicConfigProps.getFindCustomerRequest().getPartitions())
                .replicas(topicConfigProps.getFindCustomerRequest().getReplicas())
                .build();
    }
}
