package com.school.management.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StudentDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String dateOfBirth; // Change this to String for better compatibility with JSON and frontend

    private Long userId;

    public StudentDTO() {}

    public StudentDTO(String name, String email, String dateOfBirth, Long userId, Long id) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.userId = userId;
        this.id = id;
    }

    public StudentDTO(String name, String email, Date dateOfBirth, Long userId, Long id) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = formatDate(dateOfBirth);  // Convert Date to String in "yyyy-MM-dd" format
        this.userId = userId;
        this.id = id;
    }

    // Helper method to format the Date to String in "yyyy-MM-dd" format
    private String formatDate(Date dateOfBirth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dateOfBirth);
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
