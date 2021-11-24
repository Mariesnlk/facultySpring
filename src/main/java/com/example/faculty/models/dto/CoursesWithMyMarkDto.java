package com.example.faculty.models.dto;

import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.entity.User;
import lombok.Data;

@Data
public class CoursesWithMyMarkDto {
    private String name;
    private Integer duration;
    private Integer studentsAmount;
    private Topic topic;
    private User teacher;
    private String status;
    private Integer mark;

    public CoursesWithMyMarkDto(String name, Integer duration, Integer studentsAmount, Topic topic, User teacher, String status, Integer mark) {
        this.name = name;
        this.duration = duration;
        this.studentsAmount = studentsAmount;
        this.topic = topic;
        this.teacher = teacher;
        this.status = status;
        this.mark = mark;
    }
}
