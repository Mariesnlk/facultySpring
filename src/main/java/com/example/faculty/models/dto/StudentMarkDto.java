package com.example.faculty.models.dto;

import lombok.Data;

@Data
public class StudentMarkDto {
    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private Integer mark;

    public StudentMarkDto(Long id, String firstName, String secondName, String lastName, Integer mark) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.mark = mark;
    }
}
