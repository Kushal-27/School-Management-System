package com.school.management.security;

import com.school.management.model.Student;
import com.school.management.model.Teacher;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.TeacherRepository;
import com.school.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.school.management.model.User user = userRepository.findByUsername(username);

        if (user != null) {
            return User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        }


        throw new UsernameNotFoundException("User not found");
    }
}
