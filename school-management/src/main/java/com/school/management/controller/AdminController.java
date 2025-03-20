package com.school.management.controller;

import com.school.management.dto.StudentDTO;
import com.school.management.dto.TeacherDTO;
import com.school.management.dto.StudentRegistrationDTO;
import com.school.management.dto.TeacherRegistrationDTO;
import com.school.management.model.UserAction;
import com.school.management.service.StudentService;
import com.school.management.service.TeacherService;
import com.school.management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;

    private final TeacherService teacherService;

    private final UserService userService;

    public AdminController(StudentService studentService, TeacherService teacherService, UserService userService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-student")
    public StudentDTO createStudent(@RequestBody @Valid StudentRegistrationDTO studentDTO) {
        var userName = getCurrentUserName();
        return studentService.createStudent(userName, studentDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-teacher")
    public TeacherDTO createTeacher(@RequestBody @Valid TeacherRegistrationDTO teacherDTO) {
        return teacherService.createTeacher(teacherDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/students")
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/teachers")
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-student/{id}")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody @Valid StudentRegistrationDTO studentDetails) {
        var userName = getCurrentUserName();
        return studentService.updateStudent(userName, id, studentDetails);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-teacher/{id}")
    public TeacherDTO updateTeacher(@PathVariable Long id, @RequestBody @Valid TeacherRegistrationDTO teacherDetails) {
        return teacherService.updateTeacher(id, teacherDetails);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-student/{id}")
    public void deleteStudent(@PathVariable Long id) {
        var userName = getCurrentUserName();
        studentService.deleteStudent(userName, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-teacher/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }

    @GetMapping("/teachers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TeacherDTO getTeacher(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StudentDTO getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/logs")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserAction> getAllActivityLogs() {
        return userService.getAllUserLogs();
    }

    private String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        throw new SecurityException("User not authenticated");
    }

}
