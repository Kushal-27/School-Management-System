package com.school.management.event.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;

import java.io.Serializable;


@Service
public class UserActionLoggerProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.user-action}")
    private String topic;

    public UserActionLoggerProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void logUserAction(String userName, String action) {
        ActionRecord actionRecord = new ActionRecord(userName, action);
        kafkaTemplate.send(topic, actionRecord);
    }

    public record ActionRecord(String userName, String actionDescription) implements Serializable {}
}
