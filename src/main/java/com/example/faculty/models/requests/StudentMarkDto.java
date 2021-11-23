package com.example.faculty.models.requests;

import lombok.Data;

@Data
public class StudentMarkDto {
    private String firstName;
    private String secondName;
    private String lastName;
    private Integer mark;

    public StudentMarkDto(String firstName, String secondName, String lastName, Integer mark) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.mark = mark;
    }
}
