package com.school.management.event.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;


@Service
public class UserActionLoggerProducer {

    private final KafkaTemplate<String, ActionRecord> kafkaTemplate;

    @Value("${kafka.topic.teacher-action}")
    private String topic;

    public UserActionLoggerProducer(KafkaTemplate<String, ActionRecord> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void logUserAction(Long userId, String action) {
        ActionRecord actionRecord = new ActionRecord(userId, action);
        kafkaTemplate.send(topic, actionRecord);
    }

    public record ActionRecord(Long userId, String actionDescription) {}
}
