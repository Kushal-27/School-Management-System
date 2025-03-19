package com.school.management.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String subject;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Teacher(Long id, String name, String email, String subject, User user) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.user = user;
    }

    public Teacher() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}