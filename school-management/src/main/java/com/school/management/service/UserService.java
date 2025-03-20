package com.school.management.service;

import com.school.management.dto.StudentRegistrationDTO;
import com.school.management.dto.TeacherRegistrationDTO;
import com.school.management.model.Student;
import com.school.management.model.Teacher;
import com.school.management.model.User;
import com.school.management.model.UserAction;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.TeacherRepository;
import com.school.management.repository.UserActionRepository;
import com.school.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserActionRepository userActionRepository;

    public UserService(UserActionRepository userActionRepository) {
        this.userActionRepository = userActionRepository;
    }

    public List<UserAction> getAllUserLogs() {
        return userActionRepository.findAll();
    }
}