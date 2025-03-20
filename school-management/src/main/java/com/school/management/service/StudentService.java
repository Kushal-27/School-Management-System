package com.school.management.service;

import com.school.management.dto.StudentDTO;
import com.school.management.dto.StudentRegistrationDTO;
import com.school.management.event.producer.UserActionLoggerProducer;
import com.school.management.exception.AlreadyExistsException;
import com.school.management.exception.NotFoundException;
import com.school.management.model.Student;
import com.school.management.model.Teacher;
import com.school.management.model.User;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final UserActionLoggerProducer userActionLoggerProducer;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, UserActionLoggerProducer userActionLoggerProducer,
                          UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.userActionLoggerProducer = userActionLoggerProducer;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private StudentDTO mapToDTO(Student student) {
        return new StudentDTO(student.getName(), student.getEmail(), student.getDateOfBirth(), student.getUser().getId(),
                student.getId());
    }

    @Cacheable(value = "students", key = "#id")
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with id " + id));
        return mapToDTO(student);
    }

    @Transactional
    public StudentDTO createStudent(String userName, StudentRegistrationDTO studentDto) {
        if (userRepository.existsByUsername(studentDto.getUsername())) {
            throw new AlreadyExistsException("Username already taken");
        }

        User user = new User();
        user.setUsername(studentDto.getUsername());
        user.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        user.setRole(User.Role.TEACHER);
        user = userRepository.save(user);

        Student student = new Student();
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setUser(user);
        student.setDateOfBirth(studentDto.getDateOfBirth());
        var savedStudent = studentRepository.save(student);

        userActionLoggerProducer.logUserAction(userName, "Create Student with id :" + savedStudent.getId());
        return mapToDTO(savedStudent);
    }

    @CachePut(value = "students", key = "#id")
    public StudentDTO updateStudent(String userName, Long id, StudentRegistrationDTO studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setDateOfBirth(studentDetails.getDateOfBirth());

        Student updatedStudent = studentRepository.save(student);
        userActionLoggerProducer.logUserAction(userName, "Updated Student with id : " + updatedStudent.getId());

        return mapToDTO(updatedStudent);
    }

    @CacheEvict(value = "students", key = "#id")
    public void deleteStudent(String userName, Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with id " + id));

        studentRepository.delete(student);
        userActionLoggerProducer.logUserAction(userName, "Deleted Student with id : " + id);
    }

    @Cacheable(value = "students")
    public List<StudentDTO> getAllStudents() {
//        System.out.println("-----------From database -----------------");
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::mapToDTO)
                .toList();
    }
}
