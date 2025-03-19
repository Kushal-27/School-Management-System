package com.school.management.security;

import com.school.management.model.Student;
import com.school.management.model.Teacher;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(username);
        if (student != null) {
            return User.withUsername(student.getEmail())
                    .password(student.getPassword())
                    .roles(student.getRole())
                    .build();
        }

        Teacher teacher = teacherRepository.findByEmail(username);
        if (teacher != null) {
            return User.withUsername(teacher.getEmail())
                    .password(teacher.getPassword())
                    .roles(teacher.getRole())
                    .build();
        }

        throw new UsernameNotFoundException("User not found");
    }
}
