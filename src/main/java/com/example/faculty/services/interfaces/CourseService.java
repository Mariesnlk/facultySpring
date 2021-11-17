package com.example.faculty.services.interfaces;


import com.example.faculty.database.entity.Course;
import com.example.faculty.models.enums.CourseStatus;
import com.example.faculty.models.requests.CourseDto;
import com.example.faculty.util.paging.Paged;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    Course createCourse(CourseDto courseDto);

    Course updateCourse(Long courseId, CourseDto courseDto);

    Course updateCourse(Course course);

    Course findCourseById(Long courseId);

    void changeCourseStatus(Long courseId, String status);

    void deleteCourse(Long courseId);

    Paged getCoursesPage(String courseName, Integer duration, Integer studentsAmount, String topic,
                         String teacher, int pageNumber, int size, String sortType);

    Paged getCoursesPage(String courseName, Integer duration, Integer studentsAmount, String topic,
                         String teacher, String courseStatus, int pageNumber, int size, String sortType);

    Page<Course> findAllCourses(Pageable pageable);

    List<String> findAllCourseNames();

    List<Integer> findAllDurations();

    List<Integer> findAllStudentsAmount();

    List<String> findCourseNameByName(String name);

    List<String> findAllTopics();

    List<Integer> findAllTeacherNames();

    List<Integer> findTeacherIdByName(String name);

}
