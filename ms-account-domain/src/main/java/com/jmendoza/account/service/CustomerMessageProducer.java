package com.jmendoza.account.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmendoza.account.core.configuration.TopicConfigProps;
import com.jmendoza.account.core.enums.ResponseDictionary;
import com.jmendoza.account.core.exception.BadGatewayException;
import com.jmendoza.account.core.exception.CustomException;
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
public class CustomerMessageProducer {

    private final ReplyingKafkaTemplate<String, String, String> replyingTemplate;
    private final TopicConfigProps topicConfig;
    private final ObjectMapper objectMapper;


    /**
     * Obtener datos de un cliente por su ID
     *
     * @param customerId ID del cliente
     * @return Datos del cliente
     * @throws CustomException si el cliente no existe
     */
    public CustomerDto findCustomer(String customerId) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topicConfig.getFindCustomerRequest().getName(), customerId);
        RequestReplyFuture<String, String, String> replyFuture = replyingTemplate.sendAndReceive(record);
        try {
            ConsumerRecord<String, String> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
            var rawRes = consumerRecord.value();
            log.info("Cliente obtenido: {}", rawRes);
            var repsonse = objectMapper.readValue(rawRes, new TypeReference<ApiResponse<CustomerDto>>() {
            });
            var customerExists = repsonse.isOk() && repsonse.getData() != null;
            if (!customerExists) {
                throw new CustomException(repsonse.getHttpStatus(), repsonse.getCode(), repsonse.getMessage());
            }
            return repsonse.getData();
        } catch (InterruptedException | ExecutionException | TimeoutException | JsonProcessingException e) {
            log.error("Error: {}", e.getMessage());
            throw new BadGatewayException(ResponseDictionary.CUSTOMER_SERVICE_BAD_GATEWAY);
        }
    }
}
