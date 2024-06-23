package com.jmendoza.account.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmendoza.account.core.configuration.TopicConfigProps;
import com.jmendoza.account.core.enums.ResponseDictionary;
import com.jmendoza.account.core.exception.BadGatewayException;
import com.jmendoza.account.dto.ApiResponse;
import com.jmendoza.account.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final ReplyingKafkaTemplate<String, String, String> replyingTemplate;
    private final TopicConfigProps topicConfig;
    private final ObjectMapper objectMapper;

    public ApiResponse<CustomerDto> findCustomer(String customerId) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topicConfig.getFindCustomerRequest().getName(), customerId);
        RequestReplyFuture<String, String, String> replyFuture = replyingTemplate.sendAndReceive(record);
        try {
            ConsumerRecord<String, String> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
            var rawRes = consumerRecord.value();
            log.info("Cliente obtenido: {}", rawRes);
            return objectMapper.convertValue(rawRes, new TypeReference<ApiResponse<CustomerDto>>() {
            });
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Error: {}", e.getMessage());
            throw new BadGatewayException(ResponseDictionary.CUSTOMER_SERVICE_BAD_GATEWAY);
        }
    }
}
