package com.school.management.controller;

import com.school.management.model.Student;
import com.school.management.model.User;
import com.school.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or (hasRole('ROLE_STUDENT') and #id == authentication.principal.id)")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    public Student createStudent(@RequestBody Student student) {
        var userId = getCurrentUserId();
        return studentService.createStudent(userId, student);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        var userId = getCurrentUserId();
        return studentService.updateStudent(userId, id, student);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    public void deleteStudent(@PathVariable Long id) {
        var userId = getCurrentUserId();
        studentService.deleteStudent(userId, id);
    }

    private Long getCurrentUserId() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }
}
