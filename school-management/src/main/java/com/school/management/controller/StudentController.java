package com.school.management.controller;

import com.school.management.dto.StudentDTO;
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

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT') and #id == authentication.principal.id")
    public StudentDTO getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }




}
