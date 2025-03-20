package com.school.management.dto;

import java.io.Serializable;

public class TeacherDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String subject;
    private Long userId;

    public TeacherDTO() {}

    public TeacherDTO(Long id, String name, String email, String subject, Long userId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
