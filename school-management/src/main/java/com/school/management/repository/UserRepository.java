package com.school.management.repository;

import com.school.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);  // This will be used to check if the username already exists
}
