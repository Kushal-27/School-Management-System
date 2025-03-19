package com.school.management.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
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
}