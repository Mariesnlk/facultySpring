package com.example.faculty.services.interfaces;


import com.example.faculty.database.entity.Course;
import com.example.faculty.models.enums.CourseStatus;
import com.example.faculty.models.requests.CourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    Course createCourse(CourseDto courseDto);

    Course updateCourse(Long courseId, CourseDto courseDto);

    Course findCourseById(Long courseId);

    void changeCourseStatus(Long courseId, CourseStatus status);

    void deleteCourse(Long courseId);

    Page<Course> getAllCourses(Pageable pageable);

}
