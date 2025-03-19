package com.school.management.service;

import com.school.management.event.producer.UserActionLoggerProducer;
import com.school.management.model.Student;
import com.school.management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserActionLoggerProducer userActionLoggerProducer;

    @Cacheable(value = "students", key = "#id")
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    public Student createStudent(Long userId, Student student) {
        var studentResponse =  studentRepository.save(student);
        userActionLoggerProducer.logUserAction(userId, "Created Student with id : " + studentResponse.getId());
        return studentResponse;
    }

    @CachePut(value = "students", key = "#id")
    public Student updateStudent(Long userId, Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setDateOfBirth(studentDetails.getDateOfBirth());

        var updatedStudent = studentRepository.save(student);
        userActionLoggerProducer.logUserAction(userId, "Updated Student with id : " + updatedStudent.getId());

        return updatedStudent;
    }

    @CacheEvict(value = "students", key = "#id")
    public void deleteStudent(Long userId, Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        studentRepository.delete(student);

        userActionLoggerProducer.logUserAction(userId, "Deleted Student with id : " + id);

    }

    @Cacheable(value = "students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
