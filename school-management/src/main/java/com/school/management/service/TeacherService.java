package com.school.management.service;

import com.school.management.dto.TeacherDTO;
import com.school.management.dto.TeacherRegistrationDTO;
import com.school.management.event.producer.UserActionLoggerProducer;
import com.school.management.exception.AlreadyExistsException;
import com.school.management.exception.NotFoundException;
import com.school.management.model.Teacher;
import com.school.management.model.User;
import com.school.management.repository.TeacherRepository;
import com.school.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(value = "teachers", key = "#id")
    public TeacherDTO getTeacherById(Long id) {
//        System.out.println("----------------From database---------");
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found with id " + id));

        return convertToDTO(teacher);
    }

    @Transactional
    public TeacherDTO createTeacher(TeacherRegistrationDTO teacherDTO) {
        if (userRepository.existsByUsername(teacherDTO.getUsername())) {
            throw new AlreadyExistsException("Username already taken");
        }

        // Create User
        User user = new User();
        user.setUsername(teacherDTO.getUsername());
        user.setPassword(passwordEncoder.encode(teacherDTO.getPassword()));
        user.setRole(User.Role.TEACHER);
        user = userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setSubject(teacherDTO.getSubject());
        teacher.setUser(user);

        return convertToDTO(teacherRepository.save(teacher));

    }

    @CachePut(value = "teachers", key = "#id")
    public TeacherDTO updateTeacher(Long id, TeacherRegistrationDTO teacherDTO) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));

        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setSubject(teacherDTO.getSubject());

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return convertToDTO(updatedTeacher);
    }

    @CacheEvict(value = "teachers", key = "#id")
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found with id " + id));

        teacherRepository.delete(teacher);
    }

    @Cacheable(value = "teachers")
    public List<TeacherDTO> getAllTeachers() {
//        System.out.println("----------------From Database-------------------");
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private TeacherDTO convertToDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getSubject(),
                teacher.getUser().getId()
        );
    }
}
