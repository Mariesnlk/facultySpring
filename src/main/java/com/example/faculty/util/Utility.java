package com.example.faculty.util;

import com.example.faculty.models.enums.CourseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public static List<String> getAllCoursesStatus() {
        List<String> courseStatuses = new ArrayList<>();
        courseStatuses.add(CourseStatus.NOT_STARTED.name());
        courseStatuses.add(CourseStatus.STARTED.name());
        courseStatuses.add(CourseStatus.IN_PROGRESS.name());
        courseStatuses.add(CourseStatus.FINISH.name());
        return courseStatuses;
    }

}