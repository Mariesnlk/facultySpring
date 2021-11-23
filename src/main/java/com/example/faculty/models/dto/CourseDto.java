package com.example.faculty.models.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CourseDto {

    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Pattern(regexp = "[A-Za-zА-Яа-яєі]*", message = "Not valid name")
    private String name;

    @NotNull(message = "Field can't be null!")
    @Min(1)
    @Max(2)
    private Integer duration;

    @NotNull(message = "Field can't be null!")
    @Min(10)
    @Max(100)
    private Integer studentsAmount;

    @NotNull(message = "Field can't be null!")
    private Long topic;

    @NotNull(message = "Field can't be null!")
    private Long teacherId;

    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    private String status;
}