package com.school.management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}