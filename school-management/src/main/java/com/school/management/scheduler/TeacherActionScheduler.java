package com.school.management.scheduler;

import com.school.management.repository.UserActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TeacherActionScheduler {

    @Autowired
    private UserActionRepository userActionRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void clearOldLogs() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

        userActionRepository.deleteByTimestampBefore(thirtyDaysAgo);

        System.out.println("Old teacher action logs cleared successfully.");
    }
}
