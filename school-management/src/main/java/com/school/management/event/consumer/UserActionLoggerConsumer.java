package com.school.management.event.consumer;

import com.school.management.model.UserAction;
import com.school.management.repository.UserActionRepository;
import com.school.management.event.producer.UserActionLoggerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserActionLoggerConsumer {

    @Autowired
    private UserActionRepository userActionRepository;

    @KafkaListener(topics = "teacher-action", groupId = "teacher-activity-group")
    public void consume(UserActionLoggerProducer.ActionRecord actionRecord) {
        UserAction userAction = new UserAction();
        userAction.setUserId(actionRecord.userId());
        userAction.setAction(actionRecord.actionDescription());
        userAction.setTimestamp(LocalDateTime.now());

        userActionRepository.save(userAction);
    }
}
