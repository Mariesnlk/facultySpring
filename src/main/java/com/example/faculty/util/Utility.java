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

    public static List<CourseStatus> getAllCoursesStatus() {
        List<CourseStatus> courseStatuses = new ArrayList<>();
        courseStatuses.add(CourseStatus.NOT_STARTED);
        courseStatuses.add(CourseStatus.STARTED);
        courseStatuses.add(CourseStatus.IN_PROGRESS);
        courseStatuses.add(CourseStatus.FINISH);
        return courseStatuses;
    }

}