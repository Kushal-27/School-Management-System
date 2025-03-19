package com.school.management.controller;


import com.school.management.model.Student;
import com.school.management.model.Teacher;
import com.school.management.model.User;
import com.school.management.service.StudentService;
import com.school.management.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-student")
    public Student createStudent(@RequestBody Student student) {
        var userId = getCurrentUserId();
        return studentService.createStudent(userId, student);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-teacher")
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-student/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        var userId = getCurrentUserId();
        return studentService.updateStudent(userId, id, studentDetails);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-teacher/{id}")
    public Teacher updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        return teacherService.updateTeacher(id, teacherDetails);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-student/{id}")
    public void deleteStudent(@PathVariable Long id) {
        var userId = getCurrentUserId();
        studentService.deleteStudent(userId, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-teacher/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }


    private Long getCurrentUserId() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }
}
