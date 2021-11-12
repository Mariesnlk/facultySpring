package com.example.faculty.models.enums;

public enum CourseStatus {
    NOT_STARTED("Not started"),
    STARTED("Started"),
    IN_PROGRESS("In progress"),
    FINISH("Finish");

    public final String courseStatusName;

    CourseStatus(String courseStatusName) {
        this.courseStatusName = courseStatusName;
    }

}
