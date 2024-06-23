package com.jmendoza.customer.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "spring.kafka.topic")
public class TopicConfigProps {

    private Topic findCustomerRequest;
    private Topic findCustomerResponse;

    @Data
    public static class Topic {
        private String name;
        private int timeoutInSeconds;
        private int partitions;
        private short replicas;
    }
}
