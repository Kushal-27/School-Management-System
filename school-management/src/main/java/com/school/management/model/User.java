package com.school.management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)  // Ensure the username is unique
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, TEACHER, or STUDENT

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private Teacher teacher;

    public enum Role {
        ADMIN, TEACHER, STUDENT
    }
}
