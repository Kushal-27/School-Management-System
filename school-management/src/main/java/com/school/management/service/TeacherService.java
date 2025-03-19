package com.school.management.service;

import com.school.management.event.producer.UserActionLoggerProducer;
import com.school.management.model.Teacher;
import com.school.management.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserActionLoggerProducer userActionLoggerProducer;

    @Cacheable(value = "teachers", key = "#id")
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @CachePut(value = "teachers", key = "#id")
    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));

        teacher.setName(teacherDetails.getName());
        teacher.setEmail(teacherDetails.getEmail());
        teacher.setSubject(teacherDetails.getSubject());

        return teacherRepository.save(teacher);
    }

    @CacheEvict(value = "teachers", key = "#id")
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));

        teacherRepository.delete(teacher);
    }

    @Cacheable(value = "teachers")
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
}
