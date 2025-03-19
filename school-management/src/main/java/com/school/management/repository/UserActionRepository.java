package com.school.management.repository;

import com.school.management.model.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Long> {
    void deleteByTimestampBefore(LocalDateTime timestamp);
}
