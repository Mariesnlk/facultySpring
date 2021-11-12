package com.example.faculty.models.requests;

import com.example.faculty.models.enums.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CourseDto {

    @NotNull
    @NotEmpty
    private String courseName;

    @NotNull
    @NotEmpty
    private int duration;

    @NotNull
    @NotEmpty
    private int amountOfStudent;

    @NotNull
    @NotEmpty
    private Long topicId;

    @NotNull
    @NotEmpty
    private Long teacherId;

    private CourseStatus courseStatus;
}
