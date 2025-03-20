package com.school.management.controller;

import com.school.management.dto.StudentDTO;
import com.school.management.dto.StudentRegistrationDTO;
import com.school.management.dto.TeacherDTO;
import com.school.management.dto.TeacherRegistrationDTO;
import com.school.management.model.Teacher;
import com.school.management.model.User;
import com.school.management.service.StudentService;
import com.school.management.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') and #id == authentication.principal.id")
    public TeacherDTO getTeacher(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/create-student")
    public StudentDTO createStudent(@RequestBody @Valid StudentRegistrationDTO studentDTO) {
        var userName = getCurrentUserName();
        return studentService.createStudent(userName, studentDTO);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/update-student/{id}")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody @Valid StudentRegistrationDTO studentDetails) {
        var userName = getCurrentUserName();
        return studentService.updateStudent(userName, id, studentDetails);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/delete-student/{id}")
    public void deleteStudent(@PathVariable Long id) {
        var userId = getCurrentUserName();
        studentService.deleteStudent(userId, id);
    }

    @GetMapping("student/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public StudentDTO getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/students")
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    private String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        throw new SecurityException("User not authenticated");
    }
}
