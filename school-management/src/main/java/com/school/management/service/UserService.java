package com.school.management.service;

import com.school.management.dto.StudentRegistrationDTO;
import com.school.management.dto.TeacherRegistrationDTO;
import com.school.management.model.Student;
import com.school.management.model.Teacher;
import com.school.management.model.User;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.TeacherRepository;
import com.school.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to create a new user
    public User createUser(User user) {
        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        return userRepository.save(user);
    }
}