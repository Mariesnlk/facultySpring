package com.example.faculty.models.requests;

import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.entity.User;
import com.example.faculty.models.enums.CourseStatus;
import lombok.Data;

@Data
public class CourseDto {

    private String name;

    private Integer duration;

    private Integer studentsAmount;

    private Topic topicId;

    private User teacherId;

    private CourseStatus courseStatus;
}